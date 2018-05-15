package space.hypeo.networking.endpoint;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.networking.packages.Lobby;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public abstract class Endpoint implements IPlayerConnector {

    // identifies the endpoint due to its role in the connection
    private Role role = Role.NOT_CONNECTED;

    public Endpoint(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public abstract void start();

    public abstract void stop();

    public abstract void close();

    @Override
    public void movePlayer(String playerID, int position) {
    }

    @Override
    public void endTurn() {
    }

    @Override
    public int getPlayerBalance(String playerID) {
        return 0;
    }

    @Override
    public int getPlayerPosition(String playerID) {
        return 0;
    }

    @Override
    public String getCurrentPlayerID() {
        return null;
    }

    @Override
    public Lobby registeredPlayers() {
        return null;
    }

    @Override
    public void updateStageLobby() {
        // TODO: refactor duplicated code into parent class

        Log.info(role + ": updateStageLobby");

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
    }

    @Override
    public String toString() {
        return "I'm a " + role;
    }
}
