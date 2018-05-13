package space.hypeo.networking.endpoint;

import java.io.IOException;

import space.hypeo.networking.network.IHostConnector;
import space.hypeo.networking.network.Role;
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
import space.hypeo.networking.packages.Remittances;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

/**
 * This class represents the host process on a device.
 * If you don't know, if you're client or host, call WhatAmI.getRole().
 */
public class MHost extends Endpoint implements IHostConnector {

    // instance of the host
    private com.esotericsoftware.kryonet.Server server = null;

    public MHost() {
        super(Role.HOST);
    }

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
                Log.info("Host received Notification: " + notification.toString());

            } else if( object instanceof PlayerConnect) {
                Player newPlayer = (PlayerConnect) object;
                WhatAmI.addPlayerToLobby(newPlayer.getPlayerID(), newPlayer);

                Log.info("Host: player has been connected, add to lobby");
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

            } else if( object instanceof Remittances ) {
                changeBalance((Remittances) object);
            }
        }
    }

    @Override
    public void start() {
        Log.info("Server will be started.");

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

        Log.info("Server has started successfully.");
    }

    @Override
    public void stop() {
        close();
    }

    @Override
    public void close() {
        Log.info("Server will be closed.");

        try {
            server.stop();
            server.close();

        } catch( NullPointerException e ) {
            Log.warn("Server was NOT running - nothing to do!");
            Log.error(e.getMessage());
        }
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

        // TODO: check if playerID == self.playerID
        int connectionID = getConnectionID(playerID);

        Remittances remittances = new Remittances(WhatAmI.getPlayer().getPlayerID(), playerID, amount);
        server.sendToTCP(connectionID, remittances);
    }

    /**
     * Resends a received MoneyAmount from player to another player.
     * @param remittances
     */
    private void changeBalance(Remittances remittances) {

        int connectionID = getConnectionID(remittances.getReceiverId());
        server.sendToTCP(connectionID, remittances);
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

    public int getConnectionID(String playerId) throws IndexOutOfBoundsException {

        Player player = WhatAmI.getLobby().get(playerId);
        int connectionID = 0;

        if( player == null ) {
            Log.warn("Could not find player with ID '" + playerId + "' in lobby!");
            throw new IndexOutOfBoundsException("Could not find player with ID '" + playerId + "' in lobby!");
        }

        String connectionIP = player.getAddress();

        for( Connection connection : server.getConnections() ) {
            Log.info(connection.toString());
            if( connection.getRemoteAddressTCP().toString().equals(connectionIP) ) {
                connectionID = connection.getID();
            }
        }

        return connectionID;
    }
}
