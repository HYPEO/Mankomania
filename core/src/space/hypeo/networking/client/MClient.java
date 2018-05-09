package space.hypeo.networking.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import space.hypeo.networking.Endpoint;
import space.hypeo.networking.IClientConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.packages.Player;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

public class MClient extends Endpoint implements IPlayerConnector, IClientConnector {

    // TODO: next block 'static' has no effect ?!?
    static {
        System.setProperty("java.net.preferIPv6Addresses", "false");
        System.setProperty("java.net.preferIPv4Stack" , "true");
    }

    private com.esotericsoftware.kryonet.Client client;

    private List<InetAddress> discoveredHosts = null;

    // TODO: which host info is the right one?
    private Player hostInfo = null;
    private InetAddress connectedToHost = null;

    private long startPingRequest = 0;

    /**
     * Constructs instance of class MClient
     */
    public MClient() {
        super();
        // TODO: set nick and player
    }

    private class ClientListener extends Listener {

        /**
         * If has connected to host
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            hostInfo = new Player(connection, Network.Role.host);
            Log.info("hostInfo = " + hostInfo.toString());
            connectedToHost = connection.getRemoteAddressTCP().getAddress();
            Log.info("connectedToHost = " + connectedToHost);

            connection.sendTCP(new Notification("You accepted my connection to game."));
        }

        /**
         * If has diconnected from host
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            hostInfo = null;
            connectedToHost = null;
        }

        /**
         * If has reveived a package from host
         * @param connection
         * @param object
         */
        @Override
        public void received(Connection connection, Object object) {
            super.received(connection, object);

            if( object instanceof PingResponse) {
                PingResponse pingResponse = (PingResponse) object;
                Log.info("Ping time [ms] = " + (startPingRequest - pingResponse.getTime()));

            } else if( object instanceof Notification) {
                Notification notification = (Notification) object;
                Log.info("Client received: " + notification.toString());

            } else if( object instanceof Lobby ) {
                /*
                 * receive new list of Player:
                 * after connecting or disconnecting clients
                 */
                lobby = (Lobby) object;
                Log.info("Client received updated list of player");
            }
        }
    }

    @Override
    public boolean joinGame(String playerID) {
        return false;
    }

    @Override
    public void startClient() {

        // TODO: next 2 stmts have no effect ?!?
        //System.setProperty("java.net.preferIPv6Addresses", "false");
        //System.setProperty("java.net.preferIPv4Stack" , "true");

        client = new Client();
        client.start();
    }

    @Override
    public List<InetAddress> discoverHosts() {
        // use UDP port for discovering hosts
        discoveredHosts = client.discoverHosts(Network.PORT_UDP, Network.TIMEOUT_MS);
        return discoveredHosts;
    }

    public void connectToHost(InetAddress hostAddress) {

        if( client != null && discoveredHosts != null && discoveredHosts.contains(hostAddress) ) {
            try {
                client.connect(Network.TIMEOUT_MS, hostAddress.getHostAddress(), Network.PORT_TCP, Network.PORT_UDP);
                connectedToHost = hostAddress;
            } catch (IOException e) {
                e.printStackTrace();
            }

            client.addListener(new ClientListener());

            Network.register(client);

            pingServer();

            /* TODO: wait for response?
             *       is endless loop necessary?
             */
            /*while( true ) {
            }*/
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
        return nick;
    }

    @Override
    public Lobby registeredPlayers() {
        return lobby;
    }
}