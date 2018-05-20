package space.hypeo.mankomania.player;

import com.badlogic.gdx.scenes.scene2d.Stage;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.LobbyStage;
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

    // status of the player in the lobby
    private boolean ready2startGame;

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
    }

    public boolean isReady2startGame() {
        return ready2startGame;
    }

    public void setReady2startGame(boolean ready2startGame) {
        // TODO: triggered by button Ready in LobbyStage
        this.ready2startGame = ready2startGame;
    }

    public PlayerBusiness getPlayerBusiness() {
        return playerBusiness;
    }

    public void setPlayerBusiness(final PlayerBusiness playerBusiness) {
        // TODO: object from factory method
        this.playerBusiness = playerBusiness;

        // TODO: insert that player in lobby
        lobby = new Lobby(Network.MAX_PLAYER);
        lobby.add(playerBusiness.getPlayerSkeleton());
    }

    public PlayerNT getPlayerNT() {
        return playerNT;
    }

    public void setPlayerNT(final PlayerNT playerNT) {
        // TODO: object from factory method
        this.playerNT = playerNT;
    }

    public PlayerActor getPlayerActor() {
        return playerActor;
    }

    public void setPlayerActor(PlayerActor playerActor) {
        this.playerActor = playerActor;
    }

    public void updateLobby() {

        // TODO: update LobbyStage

        Stage currentStage = stageManager.getCurrentStage();

        if (currentStage instanceof LobbyStage) {
            ((LobbyStage) currentStage).updateLobby();
        }

    }
}
