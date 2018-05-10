package space.hypeo.networking.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import space.hypeo.networking.network.IClientConnector;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.networking.network.Player;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

/**
 * This class represents the client process on a device.
 * If you don't know, if you're client or host, call
 * WhatAmI.getRole() and afterwards WhatAmI.getEndpoint()
 */
public class MClient implements IPlayerConnector, IClientConnector {

    private com.esotericsoftware.kryonet.Client client;

    private List<InetAddress> discoveredHosts = null;

    // TODO: which host info is the right one?
    private Player hostInfo = null;
    private InetAddress connectedToHost = null;

    private long startPingRequest = 0;

    /**
     * This class handles the connection events with the client.
     */
    private class ClientListener extends Listener {

        /**
         * If has connected to host
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            hostInfo = new Player(connection, Role.HOST);
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
                WhatAmI.setLobby( (Lobby) object );
                Log.info("Client received updated list of player");
            } else if( object instanceof HashMap ) {
                Lobby lobby = new Lobby( (HashMap<String, Player>) object);
                WhatAmI.setLobby( lobby );
            }
        }
    }

    @Override
    public void startClient() {

        client = new Client();
        new Thread(client).start();
        Network.register(client);
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

            //pingServer();

            // TODO: create function to find out address and name

            String playerId = "01";
            String nick = "the_c_client";

            String selfAddress = "";
            try {
                selfAddress = InetAddress.getLocalHost().toString();
            } catch(UnknownHostException e) {
                Log.error(e.getMessage().toString());
            }

            Player self = new Player(playerId, nick,
                            "/" + selfAddress, selfAddress, Network.PORT_TCP,
                            Role.CLIENT);

            WhatAmI.setPlayer(self);
            WhatAmI.addPlayerToLobby( nick, self );
            WhatAmI.getLobby().print();

            Log.info("MClient-connectToHost: " + WhatAmI.getRole());
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