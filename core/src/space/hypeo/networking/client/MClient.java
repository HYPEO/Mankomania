package space.hypeo.networking.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import space.hypeo.networking.network.IClientConnector;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.NetworkAddress;
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

/**
 * This class represents the client process for an endpoint on a device.
 * If you don't know, if you're client or host, call WhatAmI.getRole().
 */
public class MClient implements IPlayerConnector, IClientConnector {

    // instance of the client
    private com.esotericsoftware.kryonet.Client client = null;

    // host, that the client is connected to
    private Player hostInfo = null;

    private long startPingRequest = 0;

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

            connection.sendTCP( new PlayerDisconnect(WhatAmI.getPlayer()) );

            hostInfo = null;
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
                WhatAmI.setLobby( (Lobby) object );
                Log.info("Client received updated list of player");

            } else if( object instanceof Acknowledge ) {
                Acknowledge ack = (Acknowledge) object;
                Log.info("Client: " + ack);

                connection.sendTCP( new PlayerConnect(WhatAmI.getPlayer()) );

            } else if( object instanceof PlayerHost) {
                hostInfo = (PlayerHost) object;
                Log.info("Client: Got connection to host " + hostInfo);
            }
        }
    }

    @Override
    public void startClient() {

        Log.info("Try to start client...");

        if( client != null ) {
            Log.warn("Client is still running - nothing to do!");
            return;
        }

        client = new Client();
        //new Thread(client).start(); // TODO: do NOT know which one is the right client-start?
        client.start();
        // register classes that can be sent/received by client
        Network.register(client);

        Log.info("Client has started successfully");
    }

    @Override
    public List<InetAddress> discoverHosts() {
        // use UDP port for discovering hosts
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
            try {
                Log.info("Client: Try to connect to " + hostAddress.toString());
                client.connect(Network.TIMEOUT_MS, hostAddress.getHostAddress(), Network.PORT_TCP, Network.PORT_UDP);

            } catch (IOException e) {
                Log.error(e.getMessage());
            }

            client.addListener(new ClientListener());

            // the client will be added to lobby after network handshake by server!

            WhatAmI.getLobby().log();

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
}