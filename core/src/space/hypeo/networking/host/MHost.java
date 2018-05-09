package space.hypeo.networking.host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import space.hypeo.networking.Endpoint;
import space.hypeo.networking.IHostConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.packages.Player;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

/**
 * This class represents the host process on a device.
 * It is implemented as singleton.
 * If you don't know, if you're client or host, call
 * WhatAmI.getRole() and afterwards WhatAmI.getEndpoint()
 */
public class MHost extends Endpoint implements IPlayerConnector, IHostConnector {

    private com.esotericsoftware.kryonet.Server server;

    private static MHost instance;

    /**
     * Constructs instance of class MHost
     */
    private MHost() {
        super();
    }

    public static MHost getInstance() {
        if( instance == null ) {
            instance = new MHost();
        }
        return instance;
    }

    private class ServerListener extends Listener {

        /**
         * If client has connected
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            if( lobby.ifFull() ) {
                // game is full
                connection.sendTCP(new Notification("Sorry, no more space for additional player left"));
                return;
            }

            Player newPlayer = new Player(connection, Network.Role.CLIENT);
            Log.info("Added new Client with: " + newPlayer.toString());

            // TODO: get the "real" nick of recently connected player
            lobby.add(newPlayer.getAddress(), newPlayer);
            connection.sendTCP(new Notification("You are connected ..."));

            lobby.print();
            // TODO: broadcast, provide current list of players
            //server.sendToAllTCP(players);
        }

        /**
         * If client has disconnected
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);

            Player leavingPlayer = new Player(connection, Network.Role.CLIENT);

            lobby.remove(leavingPlayer);

            lobby.print();
            // TODO: broadcast, provide current list of players
            //server.sendToAllTCP(players);
        }

        /**
         * If has received a package from client
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

            } else if( object instanceof Notification) {
                Notification notification = (Notification) object;
                Log.info("Host received: " + notification.toString());
            }
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
        lobby = null;
    }

    @Override
    public void startServer() {
        server = new Server();
        server.start();

        try {
            // opens a TCP and UDP server
            server.bind(Network.PORT_TCP, Network.PORT_UDP);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new ServerListener());

        Network.register(server);

        /* attach host in players */
        String selfAddress = "";
        try {
            selfAddress = InetAddress.getLocalHost().toString();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }

        nick = "the_mighty_host";
        player = new Player("/" + selfAddress, selfAddress, Network.PORT_TCP, Network.Role.HOST);
        lobby.add(nick, player);

        lobby.print();
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
