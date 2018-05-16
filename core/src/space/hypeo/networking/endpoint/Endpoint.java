package space.hypeo.networking.endpoint;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import javax.swing.text.View;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.Role;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public abstract class Endpoint  {

    // a reference to the corresponding player
    protected NetworkPlayer player;

    // identifies the endpoint due to its role in the connection
    protected Role role;

    protected StageManager stageManager;

    public Endpoint(NetworkPlayer player, Role role, StageManager stageManager) {
        this.role = role;
        this.player = player;
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
            //stageManager.push(StageFactory.getLobbyStage(viewport, stageManager, this.player));
            currentStage.act();
        }
    }
}
