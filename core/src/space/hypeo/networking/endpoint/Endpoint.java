package space.hypeo.networking.endpoint;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.Player;
import space.hypeo.networking.network.Role;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public abstract class Endpoint  {

    // a reference to the corresponding player
    Player player;

    // identifies the endpoint due to its role in the connection
    private Role role;

    public Endpoint(Player player, Role role) {
        this.role = role;
        this.player = player;
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
        /*Log.info(role + ": updateStageLobby");

        StageManager stageManager = WhatAmI.getStageManager();

        Stage currentStage = stageManager.getCurrentStage();
        Viewport viewport = currentStage.getViewport();

        if( viewport == null ) {
            Log.error(role + ": viewport must not be null!");
            return;
        }

        if( currentStage instanceof LobbyStage) {
            ((LobbyStage) currentStage).updateLobby();
            currentStage.act();
        }
        */
    }

}
