package space.hypeo.networking.host;

import java.io.IOException;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.networking.network.IHostConnector;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.networking.network.Player;
import space.hypeo.networking.packages.Acknowledge;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;
import space.hypeo.networking.packages.PlayerConnect;
import space.hypeo.networking.packages.PlayerDisconnect;
import space.hypeo.networking.packages.PlayerHost;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

/**
 * This class represents the host process on a device.
 * If you don't know, if you're client or host, call WhatAmI.getRole().
 */
public class MHost implements IPlayerConnector, IHostConnector {

    // instance of the host
    private com.esotericsoftware.kryonet.Server server = null;

    /**
     * This class handles the connection events with the server.
     */
    private class ServerListener extends Listener {

        /**
         * If client has connected.
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            if( WhatAmI.getLobby().isFull() ) {
                connection.sendTCP(new Notification("Host: Sorry, no more space for additional player left"));
                connection.close();
                return;
            }

            // send ack
            Log.info("Host: Send ack to requested client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP( new Acknowledge(WhatAmI.getPlayer().getAddress()) );

            // send host info
            Log.info("Host: Send info of myself to client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP( new PlayerHost(WhatAmI.getPlayer()) );
        }

        /**
         * If client has disconnected.
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
        }

        /**
         * If has received a package from client.
         * @param connection
         * @param object
         */
        @Override
        public void received(Connection connection, Object object) {
            super.received(connection, object);

            if( object instanceof PingRequest ) {
                PingRequest pingRequest = (PingRequest)object;
                PingResponse pingResponse = new PingResponse(pingRequest.getTime());
                connection.sendTCP(pingResponse);

            } else if( object instanceof Notification ) {
                Notification notification = (Notification) object;
                Log.info("Host received: " + notification.toString());

            } else if( object instanceof PlayerConnect) {
                Player newPlayer = (PlayerConnect) object;
                WhatAmI.addPlayerToLobby(newPlayer.getPlayerID(), newPlayer);

                Log.info("Host: received new player, add to lobby");
                WhatAmI.getLobby().log();

                server.sendToAllTCP(WhatAmI.getLobby());

                updateStageLobby();

            } else if( object instanceof PlayerDisconnect) {
                Player leavingPlayer = (PlayerDisconnect) object;
                WhatAmI.removePlayerFromLobby(leavingPlayer.getPlayerID());

                Log.info("Host: player has been disconnected, removed from lobby");
                WhatAmI.getLobby().log();

                server.sendToAllTCP(WhatAmI.getLobby());

                updateStageLobby();
            }
        }
    }

    @Override
    public void startServer() {

        Log.info("Try to start server...");

        if( server != null ) {
            Log.warn("Server is still running - nothing to do!");
            return;
        }

        server = new Server();
        // register classes that can be sent/received by server
        Network.register(server);

        try {
            // opens a TCP and UDP server
            server.bind(Network.PORT_TCP, Network.PORT_UDP);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }

        server.addListener(new ServerListener());

        // add network data of server to lobby
        WhatAmI.addPlayerToLobby( WhatAmI.getPlayer().getPlayerID(), WhatAmI.getPlayer() );
        WhatAmI.getLobby().log();

        server.start();

        Log.info("Server has started successfully");
    }

    @Override
    public void advertiseGame() {
        // TODO: start out of the lobby here if each player is ready
    }

    @Override
    public boolean startGame() {
        server.sendToAllTCP(new Notification("game starts in 5sec..."));
        return false;
    }

    @Override
    public void endGame() {
        server.sendToAllTCP(new Notification("game will be closed now..."));
        WhatAmI.getLobby().clear();
    }

    @Override
    public void changeBalance(String playerID, int amount) {

    }

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
        return WhatAmI.getPlayer().getPlayerID();
    }

    @Override
    public Lobby registeredPlayers() {
        return WhatAmI.getLobby();
    }

    @Override
    public void updateStageLobby() {
        // TODO: refactor duplicated code into parent class

        StageManager stageManager = WhatAmI.getStageManager();

        Stage currentStage = stageManager.getCurrentStage();
        Viewport viewport = currentStage.getViewport();

        if( viewport == null ) {
            Log.error("Host: viewport must not be null!");
            return;
        }

        if( currentStage instanceof LobbyStage) {
            stageManager.remove(currentStage);
        }

        stageManager.push(new LobbyStage(stageManager, viewport));
    }
}
