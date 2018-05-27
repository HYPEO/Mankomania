package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import java.util.Set;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.endpoint.IHostConnector;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.player.PlayerNT;
import space.hypeo.networking.network.Role;

public class PlayerManager {
    private final StageManager stageManager;

    private PlayerBusiness playerBusiness;
    private PlayerNT playerNT;
    private PlayerActor playerActor;

    // identifies the endpoint due to its role in the connection
    private final Role role;

    /* contains a list of all PlayerNT objects,
     * that are connected to the host of the game.
     * even the own PlayerNT object itself.
     */
    private Lobby lobby;

    public PlayerManager(final StageManager stageManager, final Role role) {
        this.stageManager = stageManager;

        playerBusiness = null;
        playerNT = null;
        playerActor = null;
        this.role = role;
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
    }

    public boolean isReady2startGame() {
        return lobby.getReadyStatus(playerBusiness.getPlayerSkeleton());
    }

    public void toggleReadyStatus() {
        Log.info(role + ": toggle ReadyStatus (old: " + lobby.getReadyStatus(playerBusiness.getPlayerSkeleton()) + ")");
        lobby.toggleReadyStatus(playerBusiness.getPlayerSkeleton());
        Log.info(role + ": toggle ReadyStatus: " + lobby.getReadyStatus(playerBusiness.getPlayerSkeleton()));
        updateLobbyStage();
        broadCastLobby();
    }

    private void broadCastLobby() {
        playerNT.broadCastLobby();
    }

    public PlayerBusiness getPlayerBusiness() {
        return playerBusiness;
    }

    public void setPlayerBusiness(final PlayerBusiness playerBusiness) {
        this.playerBusiness = playerBusiness;

        lobby = new Lobby(Network.MAX_PLAYER);
        lobby.add(playerBusiness.getPlayerSkeleton());
    }

    public PlayerNT getPlayerNT() {
        return playerNT;
    }

    public void setPlayerNT(final PlayerNT playerNT) {
        this.playerNT = playerNT;
    }

    public PlayerActor getPlayerActor() {
        return playerActor;
    }

    public void setPlayerActor(PlayerActor playerActor) {
        this.playerActor = playerActor;
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
        playerBusiness.setColor(color);
        lobby.setColor(playerBusiness.getPlayerSkeleton(), color);
        updateLobbyStage();
        broadCastLobby();
    }

    public void kickPlayer(PlayerSkeleton playerToKick) {
        Log.info("PlayerManager.kickPlayer() " + playerToKick);
        if(role == Role.HOST && playerNT != null) {
            playerNT.kickPlayerFromLobby(playerToKick);
        }
    }
}
