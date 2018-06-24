package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.player.PlayerNT;

/**
 * This class is the central manager in the business logic layer.
 * All requests from presentation layer and network layer will be computed here.
 */
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

    /**
     * Creates instance of PlayerManager.
     * @param stageManager
     * @param stageFactory
     * @param role
     */
    public PlayerManager(final StageManager stageManager, final StageFactory stageFactory, final Role role) {
        super(stageManager, stageFactory);

        playerSkeleton = null;
        playerNT = null;
        this.role = role;
    }

    /**
     * Gets instance of current PlayerSkeleton.
     * @return
     */
    public PlayerSkeleton getPlayerSkeleton() {
        return playerSkeleton;
    }

    /**
     * Sets new instance of current PlayerSkeleton.
     * @param playerSkeleton
     */
    public void setPlayerSkeleton(final PlayerSkeleton playerSkeleton) {
        this.playerSkeleton = playerSkeleton;

        lobby = new Lobby(Network.MAX_PLAYER);
        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
    }

    /**
     * Gets instance of current PlayerNT.
     * @return
     */
    public PlayerNT getPlayerNT() {
        return playerNT;
    }

    /**
     * Sets new instance of current PlayerNT.
     * @param playerNT
     */
    public void setPlayerNT(final PlayerNT playerNT) {
        this.playerNT = playerNT;
    }

    /**
     * Gets the role of current device.
     * @return
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets current lobby - contains all player connected to the host.
     * @return
     */
    public Lobby getLobby() {
        synchronized (this) {
            return lobby;
        }
    }

    /**
     * Sets the current lobby.
     * @param lobby
     */
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
     * Updates the LobbyStage.
     */
    public void updateLobbyStage() {

        Log.info(role + ": try to update LobbyStage");
        Log.info(role + ": Lobby contains " + lobby.size() + " player(s).");

        Stage currentStage = stageManager.getCurrentStage();

        // TODO: Client says: LobbyStage is not on display now ???

        if (currentStage instanceof LobbyStage) {
            Log.info(role + ": current stage is StageLobby -> update it!");
            ((LobbyStage) currentStage).triggerUpdate();

        } else {
            Log.info(role + ": LobbyStage is not on display now.");
        }
    }

    /**
     * Gets a set of currently used colors by players connected in the lobby.
     * @return
     */
    public Set<Color> usedPlayerColors() {
        return lobby.usedColors();
    }

    /**
     * Sets the color of the current player.
     * @param color
     */
    public void setColor(Color color) {
        playerSkeleton.setColor(color);
        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
        // TODO: do elsewhere -> better testing
        updateLobbyStage();
        broadCastLobby();

    }

    /**
     * Kicks a player from the lobby.
     * @param playerToKick
     */
    public void kickPlayer(PlayerSkeleton playerToKick) {
        Log.info("PlayerManager.kickPlayer() " + playerToKick);
        if (role == Role.HOST && playerNT != null) {
            playerNT.kickPlayerFromLobby(playerToKick);
        }
    }

    /**
     * Is the current player ready to start the game?
     * @return
     */
    public boolean isReady2startGame() {
        return playerSkeleton.isReady();
    }

    /**
     * Toggles the ready-to-start-the-game-status of the current player.
     */
    public void toggleReadyStatus() {
        Log.info(playerSkeleton.toString());
        playerSkeleton.setReady(!playerSkeleton.isReady());
        Log.info(playerSkeleton.toString());

        lobby.put(playerSkeleton.getPlayerID(), playerSkeleton);
        updateLobbyStage();
        broadCastLobby();
    }

    /**
     * Sends the lobby - reference by current player - broadcast over the network.
     */
    private void broadCastLobby() {
        playerNT.broadCastLobby();
    }

    @Override
    public void endTurn() {
        // TODO: Check if not calling playerActor.setInactive() here cause dice-roll problems in LocalPlayerActor...
        // Make sure nobody changes this while checking for the next player.
        synchronized (this)
        {
            for(PlayerActor playerActor : this.getPlayers())
            {
                PlayerSkeleton player = lobby.get(playerActor.getId());
                player.setxImagePosition(playerActor.getActorImageX());
                player.setyImagePosition(playerActor.getActorImageY());
            }

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
            PlayerSkeleton playerSkeleton2update = this.lobby.get(playerActor.getId());

            // Check if local player needs to broadcast balance changes.
            if (playerActor.hasPlayerBalanceChanged()) {
                playerSkeleton2update.setBalance(playerActor.getBalance());
                this.broadCastLobby();
            }

            // Update the the player's balance.
            playerActor.updateBalance(playerSkeleton2update.getBalance());


            // Update active status.
            if (playerSkeleton2update.isActive())
                playerActor.setActive();
            else
                playerActor.setInactive();

            if(playerSkeleton2update.getxImagePosition()!=0) {
                playerActor.setActorImageX(playerSkeleton2update.getxImagePosition());
                playerActor.setActorImageY(playerSkeleton2update.getyImagePosition());
            }
        }
    }

    /**
     * Creates a new PlayerActor instance and enter MapStage.
     */
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
        if(playerNT != null) {
            playerNT.startGame();
            if (this.role == Role.HOST) {
                lobby.values().iterator().next().setActive(true);
                broadCastLobby();
            }
        }
    }

    /**
     * Connects current device to the host device via network.
     * @param hostAddr
     */
    public void connectToHost(InetAddress hostAddr) {
        if(playerNT != null) {
            playerNT.connectToHost(hostAddr);
        }
    }

    /**
     * Shows the LobbyStage.
     */
    public void showLobbyStage() {
        stageManager.push(stageFactory.getLobbyStage(this));
    }

    /**
     * Disconnects the current device.
     * Host: Disconnect all clients.
     * Client: Disconnect from host.
     */
    public void disconnect() {
        if(playerNT != null) {
            playerNT.disconnect();
        }
    }

    /**
     * Discovers the network for available hosts.
     * @return list of IP addresses
     */
    public List<InetAddress> discoverHosts() {
        if(playerNT != null) {
            return playerNT.discoverHosts();

        } else {
            Log.info(role + ": PlayerNT must not be null!");
            return new ArrayList<>();
        }
    }
}
