package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import java.util.Set;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.factories.ActorFactory;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.player.PlayerNT;
import space.hypeo.networking.network.Role;

public class PlayerManager extends GameStateManager {

    private PlayerSkeleton playerSkeleton;
    private PlayerNT playerNT;

    // identifies the endpoint due to its role in the connection
    private final Role role;

    /* contains a list of all PlayerNT objects,
     * that are connected to the host of the game.
     * even the own PlayerNT object itself.
     */
    private Lobby lobby;

    // TODO: inject playerSkeleton, playerNT by contructor?
    public PlayerManager(final StageManager stageManager, final StageFactory stageFactory, final Role role) {
        super(stageManager, stageFactory);

        playerSkeleton = null;
        playerNT = null;
        this.role = role;
    }

    public PlayerSkeleton getPlayerSkeleton() {
        return playerSkeleton;
    }

    public void setPlayerSkeleton(final PlayerSkeleton playerSkeleton) {
        this.playerSkeleton = playerSkeleton;

        lobby = new Lobby(Network.MAX_PLAYER);
        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
    }

    public PlayerNT getPlayerNT() {
        return playerNT;
    }

    public void setPlayerNT(final PlayerNT playerNT) {
        this.playerNT = playerNT;
    }

    public Role getRole() {
        return role;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        synchronized (this) {
            this.lobby = lobby;
            Log.info(role + ": new lobby object was set");

            updatePlayerSkeleton();
        }
    }

    /**
     * Updates the PlayerSkeleton in the PlayerManager.
     * Case: Lobby received.
     */
    private void updatePlayerSkeleton() {
        Log.info(role + ": updatePlayerSkeleton");

        if (playerSkeleton == null || lobby.isEmpty()) {
            return;
        }

        PlayerSkeleton found = lobby.get(playerSkeleton.getPlayerID());

        if (found == null) {
            return;
        }

        /**
         * Attributes: ID, Nick, Address, isReady, Color MUST NOT be change from outside!
         */

        /* balance */
        if (playerSkeleton.getBalance() != found.getBalance()) {
            Log.info(role + ": updatePlayerSkeleton-setBalance");
            playerSkeleton.setBalance(found.getBalance());
        }
    }

    /**
     * Changes the current player, then update the lobby.
     * Case: Send lobby after updating.
     *
     * @param playerId
     * @param balance
     */
    public void changeBalance(String playerId, int balance) {

        if (playerSkeleton.getPlayerID().equals(playerId)) {
            /* change own balanace */
            playerSkeleton.setBalance(balance);
            lobby.put(playerId, playerSkeleton);

        } else {
            /* change balance of other player */
            PlayerSkeleton p2changeBalance = lobby.get(playerId);
            if(p2changeBalance != null) {
                p2changeBalance.setBalance(balance);
                lobby.put(playerId, p2changeBalance);
            }
        }

        /* publish changes in lobby */
        broadCastLobby();
    }

    public void updateLobbyStage() {

        Log.info(role + ": try to update LobbyStage");

        Stage currentStage = stageManager.getCurrentStage();

        if (currentStage instanceof LobbyStage) {
            Log.info(role + ": current stage is StageLobby -> update it!");
            ((LobbyStage) currentStage).updateLobby();
        }
    }

    public Set<Color> usedPlayerColors() {
        return lobby.usedColors();
    }

    public void setColor(Color color) {
        playerSkeleton.setColor(color);
        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
        // TODO: do elsewhere -> better testing
        updateLobbyStage();
        broadCastLobby();

    }

    public void kickPlayer(PlayerSkeleton playerToKick) {
        Log.info("PlayerManager.kickPlayer() " + playerToKick);
        if (role == Role.HOST && playerNT != null) {
            playerNT.kickPlayerFromLobby(playerToKick);
        }
    }

    public boolean isReady2startGame() {
        return playerSkeleton.isReady();
    }

    public void toggleReadyStatus() {
        Log.info(playerSkeleton.toString());
        playerSkeleton.setReady(!playerSkeleton.isReady());
        Log.info(playerSkeleton.toString());

        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
        updateLobbyStage();
        broadCastLobby();
    }

    private void broadCastLobby() {
        playerNT.broadCastLobby();
    }

    @Override
    public void endTurn() {
        // TODO: Check if not calling playerActor.setInactive() here cause dice-roll problems in LocalPlayerActor...
        // Make sure nobody changes this while checking for the next player.
        synchronized (this)
        {
            PlayerSkeleton nextPlayer = null;
            PlayerSkeleton currentPlayer = null;

            for(PlayerSkeleton skeleton: lobby.values()) {
                if(currentPlayer != null) {
                    // Current player has been found in last iteration.
                    // This is the next player, set and exit loop.
                    nextPlayer = skeleton;
                    break;
                }

                // Check if this is the current player, if yes, remember player...
                if(skeleton.isActive())
                    currentPlayer = skeleton;
            }

            // If there is no current player, we cannot find the next.
            if(currentPlayer == null)
                throw new IllegalStateException("No player is currently active, cannot find next Player");

            // If there is no next player,
            // then active player was at the end of the collection
            // This means that nextPlayer is the first player in the collection.
            if(nextPlayer==null)
                nextPlayer = lobby.values().iterator().next();

            // Update status for both players.
            currentPlayer.setActive(false);
            nextPlayer.setActive(true);

            // Broadcast new lobby...
            broadCastLobby();
        }
    }

    @Override
    public void updatePlayer(PlayerActor playerActor) {
        // Make sure nobody changes lobby or anything else.
        synchronized (this) {
            PlayerSkeleton playerSkeleton = this.lobby.get(playerActor.getId());

            // Check if local player needs to broadcast balance changes.
            if (playerSkeleton.getBalance() != playerActor.getBalance()) {
                this.changeBalance(playerActor.getId(), playerActor.getBalance());
            }

            playerActor.setBalance(playerSkeleton.getBalance());

            // Update active status.
            if (playerSkeleton.isActive())
                playerActor.setActive();
            else
                playerActor.setInactive();
        }
    }

    public void createPlayerActor() {
        Log.info(role + ": try to get LobbyStage");

        Stage currentStage = stageManager.getCurrentStage();

        if (currentStage instanceof LobbyStage) {
            Log.info(role + ": current stage is StageLobby -> create MapStage!");
            ((LobbyStage) currentStage).triggerMapStage();
        }
    }

    /**
     * Shows the view HorseRaceResultStage and displays the winner.
     *
     * @param horseName name of the winning horse.
     */
    public void showHorseRaceResultStage(String horseName) {
        // TODO: not implemented yet
        //stageManager.push(stageFactory.getHorseRaceResultStage(1,1, horseName));
    }

    /**
     * Sends the name of the winning horse over the network.
     *
     * @param horseName name of the winning horse.
     */
    public void sendHorseRaceResult(String horseName) {
        playerNT.sendHorseRaceResult(horseName);
    }

    /**
     * Shows the view RouletteResultStage and displays the slot number.
     *
     * @param slotId number of the winning slot.
     */
    public void showRouletteResultStage(int slotId) {
        // TODO: not implemented yet
        //stageManager.push(stageFactory.getRouletteResultStage(1));
    }

    /**
     * Sends the id of the winning slot over the network.
     *
     * @param slotId number of the winning slot.
     */
    public void sendRouletteResult(int slotId) {
        playerNT.sendRouletteResult(slotId);
    }

    @Override
    public void startGame(){
        playerNT.startGame();
        if(this.role == Role.HOST) {
            lobby.values().iterator().next().setActive(true);
            broadCastLobby();
        }
    }
}
