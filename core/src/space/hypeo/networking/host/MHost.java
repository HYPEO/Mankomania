package space.hypeo.networking.host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import space.hypeo.networking.Endpoint;
import space.hypeo.networking.IHostConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.PlayerInfo;
import space.hypeo.networking.Players;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class MHost extends Endpoint implements IPlayerConnector, IHostConnector {

    private com.esotericsoftware.kryonet.Server server;

    /**
     * Constructs instance of class MHost
     */
    public MHost() {
        super();
    }

    private class ServerListener extends Listener {

        /**
         * If client has connected
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            if( players.size() >= Network.MAX_PLAYER ) {
                // game is full
                // send message to client: you can not join game, game is full
                connection.sendTCP(new Notification("Sorry, no more space for additional player left"));
                return;
            }

            PlayerInfo newPlayer = new PlayerInfo(connection, Network.Role.client);
            Log.info("Added new Client with: " + newPlayer.toString());

            players.put(newPlayer.getAddress(), newPlayer);
            connection.sendTCP(new Notification("You are connected ..."));

            players.print();
            // TODO: broadcast, provide current list of players
        }

        /**
         * If client has disconnected
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);

            PlayerInfo leavingPlayer = new PlayerInfo(connection, Network.Role.client);

            players.remove(leavingPlayer);
            // TODO: broadcast, provide current list of players
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
        // TODO: start the lobby here
    }

    @Override
    public boolean startGame() {
        //server.sendToAllTCP("game starts in 5sec...");
        return false;
    }

    @Override
    public void endGame() {
        //server.sendToAllTCP("game will be closed now...");
        players = null;
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

        /* attach PlayerInfo of host in players */
        String selfAddress = "";
        try {
            selfAddress = InetAddress.getLocalHost().toString();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
        PlayerInfo self = new PlayerInfo("/" + selfAddress, selfAddress, Network.PORT_TCP, Network.Role.host);
        players.put("the_mighty_host", self);

        players.print();
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
        return null;
    }

    @Override
    public Players registeredPlayers() {
        return players;
    }
}
