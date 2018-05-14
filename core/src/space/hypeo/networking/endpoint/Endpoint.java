package space.hypeo.networking.endpoint;

import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.Role;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public abstract class Endpoint  {

    // a reference to the corresponding player
    NetworkPlayer player;

    // identifies the endpoint due to its role in the connection
    private Role role;

    public Endpoint(NetworkPlayer player, Role role) {
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
