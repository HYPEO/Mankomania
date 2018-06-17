package space.hypeo.networking.endpoint;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.NetworkAddress;
import space.hypeo.networking.packages.Acknowledge;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.HorseRaceResult;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;
import space.hypeo.networking.packages.PlayerConnect;
import space.hypeo.networking.packages.PlayerHost;
import space.hypeo.networking.packages.PlayerDisconnect;
import space.hypeo.networking.packages.RouletteResult;
import space.hypeo.networking.packages.StartGame;

/**
 * This class represents the client process on the device.
 */
public class MClient implements IEndpoint, IClientConnector {
    private PlayerManager playerManager;

    // instance of the client
    private com.esotericsoftware.kryonet.Client client;

    // host, that the client is connected to
    private PlayerHost hostPlayer;

    private long startPingRequest = 0;

    /**
     * Creates a new instance and starts client.
     * @param playerManager
     */
    public MClient(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.start();
    }

    /**
     * This class handles the connection events with the client.
     */
    private class ClientListener extends Listener {

        /**
         * If has connected to host.
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            connection.sendTCP(new Notification("You accepted my connection to game."));
        }

        /**
         * If has diconnected from host.
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);

            connection.sendTCP( new PlayerDisconnect(playerManager.getPlayerSkeleton()) );

            hostPlayer = null;
            connection.close();
        }

        /**
         * If has reveived a package from host.
         * @param connection
         * @param object
         */
        @Override
        public void received(Connection connection, Object object) {
            super.received(connection, object);

            PlayerSkeleton myself = playerManager.getPlayerSkeleton();

            if(object instanceof PingResponse) {
                PingResponse pingResponse = (PingResponse) object;
                Log.info("Ping time [ms] = " + (startPingRequest - pingResponse.getTime()));

            } else if(object instanceof Notification) {
                Notification notification = (Notification) object;
                Log.info("Client: Received notification: " + notification.toString());

            } else if(object instanceof Lobby) {
                playerManager.setLobby( (Lobby) object );
                Log.info("Client: Received updated lobby");
                playerManager.updateLobbyStage();

            } else if(object instanceof Acknowledge) {
                Acknowledge ack = (Acknowledge) object;
                Log.info("Client: Received ACK from " + ack);

                connection.sendTCP(new PlayerConnect(myself));

            } else if(object instanceof PlayerHost) {
                hostPlayer = (PlayerHost) object;
                Log.info("Client: Received info of host, to be connected with: " + hostPlayer);

            } else if(object instanceof PlayerDisconnect) {
                PlayerDisconnect playerDisconnect = (PlayerDisconnect) object;
                Log.info("Client: Received order to disconnect from host");

                Log.info("Client: Order is for me ");
                client.sendTCP(playerDisconnect);
                close();

            } else if(object instanceof StartGame) {
                Log.info("Client: Received order to start the game");
                playerManager.createPlayerActor();
            } else if(object instanceof HorseRaceResult) {
                Log.info("Client: Received new winner of horse race.");
                HorseRaceResult winner = (HorseRaceResult) object;
                playerManager.showHorseRaceResultStage(winner.getHorseName());

            } else if(object instanceof RouletteResult) {
                Log.info("Client: Received new winner slot of roulette.");
                RouletteResult winnerSlotId = (RouletteResult) object;
                playerManager.showRouletteResultStage(winnerSlotId.getResultNo());

            }
        }
    }

    /**
     * Starts the client network thread.
     * This thread is what receives (and sometimes sends) data over the network
     */
    private void start() {
        Log.info("Client will be started.");

        if( client != null ) {
            Log.warn("Client is still running - nothing to do!");
            return;
        }

        client = new Client();
        client.start();
        // register classes that can be sent/received by client
        Network.register(client);

        Log.info("Client has started successfully.");
    }

    /**
     * Closes any network connection AND stops the client network thread.
     */
    public void stop() {
        Log.info("Client will be stopped.");

        try {
            client.close();
            client.stop();

        } catch( NullPointerException e ) {
            Log.warn("Client was NOT running - nothing to do!");
            Log.error(e.getMessage());
        }
    }

    /**
     * Closes the network connection BUT does NOT stop the client network thread.
     * Client can reconnect or connect to a different server.
     */
    public void close() {
        Log.info("Client will be closed.");
        client.close();
    }

    @Override
    public List<InetAddress> discoverHosts() {
        // TODO: check if WLAN has "Wireless Isolation" enabled => no discovery possible
        // use UDP port for discovering hosts
        Log.info("Client: Searching in WLAN for hosts...");
        List<InetAddress> discoveredHosts = client.discoverHosts(Network.PORT_UDP, Network.TIMEOUT_MS);
        discoveredHosts = NetworkAddress.filterLoopback(discoveredHosts);
        return discoveredHosts;
    }

    /**
     * Establishes a connection to given host.
     * @param hostAddress host to connect to
     */
    public void connectToHost(InetAddress hostAddress) {

        if( client != null && hostAddress != null ) {
            Log.info("Client: Try to connect to " + hostAddress.toString());

            try {
                client.connect(Network.TIMEOUT_MS, hostAddress.getHostAddress(), Network.PORT_TCP, Network.PORT_UDP);
                Log.info("Client: Connection to host " + hostAddress + " established");

            } catch (IOException e) {
                Log.error(e.getMessage());
            }

            client.addListener(new ClientListener());
            // the client will be added to lobby after network handshake by server!

        } else {
            Log.info("Client has NOT been initialized yet!");
        }
    }

    /**
     * Sends a PingRequest to server.
     */
    public void pingServer() {
        PingRequest pingRequest = new PingRequest();
        startPingRequest = pingRequest.getTime();

        client.sendTCP(pingRequest);
    }

    @Override
    public boolean joinGame(String playerID) {
        return false;
    }

    @Override
    public void broadCastLobby() {
        client.sendTCP(playerManager.getLobby());
    }

    @Override
    public void sendHorseRaceResult(String horseName) {
        HorseRaceResult winner = new HorseRaceResult(playerManager.getPlayerSkeleton());
        winner.setHorseName(horseName);
        client.sendTCP(winner);
    }

    @Override
    public void sendRouletteResult(int slotId) {
        RouletteResult winnerSlotId = new RouletteResult(playerManager.getPlayerSkeleton());
        winnerSlotId.setResultNo(slotId);
        client.sendTCP(winnerSlotId);
    }
}