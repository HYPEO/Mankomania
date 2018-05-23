package space.hypeo.networking.endpoint;

import java.io.IOException;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.packages.Acknowledge;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;
import space.hypeo.networking.packages.PlayerConnect;
import space.hypeo.networking.packages.PlayerDisconnect;
import space.hypeo.networking.packages.PlayerHost;
import space.hypeo.networking.packages.PlayerToggleReadyStatus;
import space.hypeo.networking.packages.Remittances;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

/**
 * This class represents the host process on the device.
 */
public class MHost  implements IEndpoint, IHostConnector {
    private PlayerManager playerManager;

    // instance of the host
    private com.esotericsoftware.kryonet.Server server;

    /**
     * Creates a new instance and starts server.
     * @param playerManager
     */
    public MHost(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.start();
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

            if( playerManager.getLobby().isFull() ) {
                connection.sendTCP(new Notification("Host: Sorry, no more space for additional player left"));
                connection.close();
                return;
            }

            // send ack to client
            Log.info("Host: Send ack to requested client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP( new Acknowledge(playerManager.getPlayerBusiness()) );

            // send host info
            Log.info("Host: Send info of myself to client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP( new PlayerHost(playerManager.getPlayerBusiness().getPlayerID()) );
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
                PlayerSkeleton newPlayer = (PlayerConnect) object;
                playerManager.getLobby().add(newPlayer);

                Log.info("Host: player has been connected, add to lobby");
                playerManager.getLobby().log();

                server.sendToAllTCP(playerManager.getLobby());

                playerManager.updateLobby();

            } else if( object instanceof PlayerDisconnect) {
                PlayerSkeleton leavingPlayer = (PlayerDisconnect) object;
                playerManager.getLobby().remove(leavingPlayer);

                Log.info("Host: player has been disconnected, removed from lobby");
                playerManager.getLobby().log();

                server.sendToAllTCP(playerManager.getLobby());

                playerManager.updateLobby();

            } else if( object instanceof PlayerToggleReadyStatus) {
                PlayerSkeleton toggleStatusPlayer = (PlayerSkeleton) object;

                Log.info("Host: toggle ready status of player " + toggleStatusPlayer);
                toggleReadyStatus(toggleStatusPlayer);

                playerManager.updateLobby();
            }
        }
    }

    private void start() {
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

        server.start();

        Log.info("Server has started successfully.");
    }

    @Override
    public void stop() {
        stop();
    }

    public void close() {
        Log.info("Server will be closed.");

        try {
            server.close();

        } catch( NullPointerException e ) {
            Log.warn("Server was NOT running - nothing to do!");
            Log.error(e.getMessage());
        }

        Log.info("Server closed.");
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
    }

    @Override
    public void changeBalance(Remittances remittances) {
        // TODO: correct that process!
        int connectionID = getConnectionID(remittances.getReceiverId());
        server.sendToTCP(connectionID, remittances);
    }


    public int getConnectionID(String playerId) throws IllegalArgumentException {

        PlayerSkeleton needle = playerManager.getLobby().contains(playerId);
        int connectionID = 0;

        if( needle == null ) {
            Log.warn("Could not find player with ID '" + playerId + "' in lobby!");
            throw new IllegalArgumentException("Could not find player with ID '" + playerId + "' in lobby!");
        }

        String connectionIP = needle.getAddress();

        for( Connection connection : server.getConnections() ) {
            Log.info(connection.toString());
            if( connection.getRemoteAddressTCP().toString().equals(connectionIP) ) {
                connectionID = connection.getID();
            }
        }

        return connectionID;
    }

    @Override
    public void toggleReadyStatus(PlayerSkeleton player2toggleReadyStatus) {
        // TODO: correct that process!
        Lobby lobby = playerManager.getLobby();
        lobby.toggleReadyStatus(player2toggleReadyStatus);

        server.sendToAllTCP(lobby);
    }
}
