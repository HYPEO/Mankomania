package space.hypeo.networking.endpoint;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import space.hypeo.mankomania.player.Lobby;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Acknowledge;
import space.hypeo.networking.packages.HorseRaceResult;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;
import space.hypeo.networking.packages.PlayerConnect;
import space.hypeo.networking.packages.PlayerDisconnect;
import space.hypeo.networking.packages.PlayerHost;
import space.hypeo.networking.packages.RouletteResult;
import space.hypeo.networking.packages.StartGame;

/**
 * This class represents the host process on the device.
 */
public class MHost implements IEndpoint, IHostConnector {
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
            connection.sendTCP( new Acknowledge(playerManager.getPlayerSkeleton()) );

            // send host info
            Log.info("Host: Send info of myself to client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP( new PlayerHost(playerManager.getPlayerSkeleton().getPlayerID()) );
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

            if(object instanceof PingRequest) {
                PingRequest pingRequest = (PingRequest) object;
                PingResponse pingResponse = new PingResponse(pingRequest.getTime());
                connection.sendTCP(pingResponse);

            } else if(object instanceof Notification) {
                Notification notification = (Notification) object;
                Log.info("Host: received Notification: " + notification.toString());

            } else if(object instanceof PlayerConnect) {
                PlayerSkeleton newPlayer = (PlayerConnect) object;
                playerManager.getLobby().put(newPlayer.getPlayerID(), newPlayer);

                Log.info("Host: player has been connected, add to lobby");
                playerManager.getLobby().log();

                broadCastLobby();

            } else if(object instanceof PlayerDisconnect) {
                PlayerSkeleton leavingPlayer = (PlayerDisconnect) object;
                playerManager.getLobby().remove(leavingPlayer);

                Log.info("Host: player has been disconnected, removed from lobby");
                playerManager.getLobby().log();

                broadCastLobby();

            } else if( object instanceof Lobby) {
                Log.info("Host: Received updated lobby");
                Lobby lobby = (Lobby) object;
                playerManager.setLobby(lobby);

                Log.info("Host: Broadcast updated lobby");
                server.sendToAllExceptTCP(connection.getID(), lobby);

                Log.info("Host: update own lobby");
                playerManager.updateLobbyStage();

            } else if(object instanceof HorseRaceResult) {
                Log.info("Host: Received new winner of horse race.");
                HorseRaceResult winner = (HorseRaceResult) object;

                server.sendToAllExceptTCP(connection.getID(), winner);
                playerManager.showHorseRaceResultStage(winner.getHorseName());

            } else if(object instanceof RouletteResult) {
                Log.info("Host: Received new winner slot of roulette.");
                RouletteResult winnerSlotId = (RouletteResult) object;

                server.sendToAllExceptTCP(connection.getID(), winnerSlotId);
                playerManager.showRouletteResultStage(winnerSlotId.getResultNo());
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
        if(server != null) {
            server.stop();
        }
    }

    public void close() {
        Log.info("Server will be closed.");

        try {
            server.close();
            server = null;

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
        server.sendToAllTCP(new StartGame());
        return true;
    }

    @Override
    public void endGame() {
        server.sendToAllTCP(new Notification("game will be closed now..."));
    }

    public int getConnectionID(String playerId) {

        PlayerSkeleton needle = playerManager.getLobby().get(playerId);
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
    public void broadCastLobby() {
        /* changed own lobby, then broadcast */
        Log.info("Host: send broadcast new lobby");
        server.sendToAllTCP(playerManager.getLobby());

        // TODO: inject lobby as parameter?
        Log.info("Host: update own lobby");
        playerManager.updateLobbyStage();
    }

    @Override
    public void sendOrderToCloseConnection(PlayerSkeleton playerToKick) {
        Log.info("MHost: Send order to client '" + playerToKick + "' to close connection to host");

        int connectionID = getConnectionID(playerToKick.getPlayerID());
        PlayerDisconnect playerDisconnect = new PlayerDisconnect(playerToKick);

        // TODO: next line has no effect?!
        server.sendToTCP(connectionID, playerDisconnect);
    }

    @Override
    public void sendHorseRaceResult(String horseName) {
        HorseRaceResult winner = new HorseRaceResult(playerManager.getPlayerSkeleton());
        winner.setHorseName(horseName);
        server.sendToAllTCP(winner);
    }

    @Override
    public void sendRouletteResult(int slotId) {
        RouletteResult winnerSlotId = new RouletteResult(playerManager.getPlayerSkeleton());
        winnerSlotId.setResultNo(slotId);
        server.sendToAllTCP(slotId);
    }
}
