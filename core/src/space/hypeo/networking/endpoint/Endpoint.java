package space.hypeo.networking.endpoint;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.RawPlayer;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.packages.Remittances;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public abstract class Endpoint  {

    // a reference to the corresponding networkPlayer
    protected NetworkPlayer networkPlayer;

    // identifies the endpoint due to its role in the connection
    protected Role role;

    protected StageManager stageManager;

    public Endpoint(NetworkPlayer networkPlayer, Role role, StageManager stageManager) {
        this.role = role;
        this.networkPlayer = networkPlayer;
        this.stageManager = stageManager;
    }

    public Role getRole() {
        return role;
    }

    public abstract void start();

    public abstract void stop();

    public abstract void close();

    /**
     * Update view of game lobby.
     */
    public void updateStageLobby() {
        Log.info(role + ": updateStageLobby");

        Stage currentStage = stageManager.getCurrentStage();
        //Viewport viewport = currentStage.getViewport();

        if( currentStage instanceof LobbyStage) {
            ((LobbyStage) currentStage).updateLobby();
            //stageManager.remove(currentStage);
            //stageManager.push(StageFactory.getLobbyStage(viewport, stageManager, this.networkPlayer));
            currentStage.act();
        }
    }

    /**
     * Toggles the status of a player in the lobby between:
     * ready2play <-> not_ready2play
     * @param player2toggleStatus name of the player
     */
    public abstract void toggleReadyStatus(RawPlayer player2toggleStatus);

    /**
     * Resends a received MoneyAmount from player to another player.
     * @param remittances
     */
    public abstract void changeBalance(Remittances remittances);
}
