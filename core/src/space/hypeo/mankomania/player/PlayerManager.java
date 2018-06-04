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
        this.lobby = lobby;
        Log.info(role + ": new lobby object was set");

        updatePlayerSkeleton();
    }

    /**
     * Updates the PlayerSkeleton in the PlayerManager.
     * Case: Lobby received.
     */
    private void updatePlayerSkeleton() {
        Log.info(role + ": updatePlayerSkeleton");

        if(playerSkeleton == null || lobby.isEmpty()) {
            return;
        }

        PlayerSkeleton found = lobby.get(playerSkeleton.getPlayerID());

        if(found == null) {
            return;
        }

        /**
         * Attributes: ID, Nick, Address, isReady, Color MUST NOT be change from outside!
         */

        /* balance */
        if(playerSkeleton.getBalance() != found.getBalance()) {
            Log.info(role + ": updatePlayerSkeleton-setBalance");
            playerSkeleton.setBalance(found.getBalance());
        }
    }

    /**
     * Changes the current player, then update the lobby.
     * Case: Send lobby after updating.
     * @param playerId
     * @param balance
     */
    public void changeBalance(String playerId, int balance) {
        if(playerSkeleton.getPlayerID().equals(playerId)) {
            playerSkeleton.setBalance(balance);
        }

        lobby.put(playerId, playerSkeleton);
        broadCastLobby();
    }

    public void updateLobbyStage() {

        Log.info(role + ": try to update LobbyStage");

        Stage currentStage = stageManager.getCurrentStage();

        if(currentStage instanceof LobbyStage) {
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
        if(role == Role.HOST && playerNT != null) {
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
        nextPlayer();
    }

    private void nextPlayer() {
        // TODO: complete method
        /*playerActors.add(activePlayer);
        activePlayer.setInactive();
        activePlayer = playerActors.remove();
        activePlayer.setActive();*/
    }

    public void createPlayerActor() {

        if(!lobby.areAllPlayerReady()) {
            Log.info("Not all player are ready to start game!");
            return;
        }

        ActorFactory actorFactory = new ActorFactory(stageManager);

        Log.info(role + ": lobby contains " + lobby.size() + " player.");
        lobby.log();

        Log.info(role + ": playerActors contains " + playerActors.size() + " players.");

        for(PlayerActor pa : playerActors) {
            Log.info(" " + pa);
        }

        Log.info(role + ": playerActors contains " + playerActors.size() + " players.");

        for(PlayerActor pa : playerActors) {
            Log.info(" " + pa);
        }

        for(PlayerSkeleton ps : lobby.values()) {
            /* add all player except myself */
            //if(!ps.equals(playerSkeleton)) {
                Log.info("create from PlayerSkeleton " + ps + " a PlayerActor");
                addPlayer(
                        actorFactory.getPlayerActor(
                                ps.getPlayerID(), ps.getNickname(), ps.getColor(),
                                (ps.equals(playerSkeleton)) ? true : false,
                                this, stageFactory)
                );
            //}
        }

        Log.info(role + ": playerActors contains " + playerActors.size() + " players.");

        playerNT.startGame();

        stageManager.push(stageFactory.getCreatePlayerActorStage(this));
    }
}
