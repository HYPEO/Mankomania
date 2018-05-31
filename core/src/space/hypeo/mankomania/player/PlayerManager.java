package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import java.util.Set;

import javax.print.DocFlavor;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.player.PlayerNT;
import space.hypeo.networking.network.Role;

public class PlayerManager {
    private final StageManager stageManager;

    private PlayerSkeleton playerSkeleton;
    private PlayerNT playerNT;

    // identifies the endpoint due to its role in the connection
    private final Role role;

    /* contains a list of all PlayerNT objects,
     * that are connected to the host of the game.
     * even the own PlayerNT object itself.
     */
    private Lobby lobby;

    public PlayerManager(final StageManager stageManager, final Role role) {
        this.stageManager = stageManager;

        playerSkeleton = null;
        playerNT = null;
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
        return lobby.getReadyStatus(playerSkeleton);
    }

    public void toggleReadyStatus() {
        Log.info(role + ": toggle ReadyStatus (old: " + lobby.getReadyStatus(playerSkeleton) + ")");
        lobby.toggleReadyStatus(playerSkeleton);
        Log.info(role + ": toggle ReadyStatus: " + lobby.getReadyStatus(playerSkeleton));
        updateLobbyStage();
        broadCastLobby();
    }

    private void broadCastLobby() {
        playerNT.broadCastLobby();
    }

    public PlayerSkeleton getPlayerSkeleton() {
        return playerSkeleton;
    }

    public void setPlayerSkeleton(final PlayerSkeleton playerSkeleton) {
        this.playerSkeleton = playerSkeleton;

        lobby = new Lobby(Network.MAX_PLAYER);
        lobby.add(playerSkeleton);
    }

    public PlayerNT getPlayerNT() {
        return playerNT;
    }

    public void setPlayerNT(final PlayerNT playerNT) {
        this.playerNT = playerNT;
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
        lobby.setColor(playerSkeleton, color);
        updateLobbyStage();
        broadCastLobby();
    }

    public void kickPlayer(PlayerSkeleton playerToKick) {
        Log.info("PlayerManager.kickPlayer() " + playerToKick);
        if(role == Role.HOST && playerNT != null) {
            playerNT.kickPlayerFromLobby(playerToKick);
        }
    }

    public void changeBalance(String playerId, int balance) {
        playerNT.changeBalance(playerId, balance);
    }
}
