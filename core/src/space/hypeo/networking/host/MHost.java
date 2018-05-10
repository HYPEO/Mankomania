package space.hypeo.networking.host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import space.hypeo.networking.network.IHostConnector;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.networking.network.Player;
import space.hypeo.networking.packages.Acknowledge;
import space.hypeo.networking.packages.DisconnectPlayer;
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
 * If you don't know, if you're client or host, call
 * WhatAmI.getRole() and afterwards WhatAmI.getEndpoint()
 */
public class MHost implements IPlayerConnector, IHostConnector {

    private com.esotericsoftware.kryonet.Server server;

    /**
     * This class handles the connection events with the server.
     */
    private class ServerListener extends Listener {

        /**
         * If client has connected
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
            connection.sendTCP( new Acknowledge(WhatAmI.getPlayer().getAddress().toString()) );

            // send host info
            Log.info("Host: Send info of myself to client ip " + connection.getRemoteAddressTCP().toString());
            connection.sendTCP(WhatAmI.getPlayer());
        }

        /**
         * If client has disconnected
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
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

            } else if( object instanceof Notification ) {
                Notification notification = (Notification) object;
                Log.info("Host received: " + notification.toString());

            } else if( object instanceof Player ) {
                Player newPlayer = (Player) object;
                WhatAmI.addPlayerToLobby(newPlayer.getPlayerID(), newPlayer);

                Log.info("Host: received new player, add to lobby");
                WhatAmI.getLobby().print();

                server.sendToAllTCP(WhatAmI.getLobby());

            } else if( object instanceof DisconnectPlayer ) {
                Player leavingPlayer = (Player) object;
                WhatAmI.removePlayerFromLobby(leavingPlayer.getPlayerID());

                Log.info("Host: player has been disconnected, removed from lobby");
                WhatAmI.getLobby().print();

                server.sendToAllTCP(WhatAmI.getLobby());
            }
        }
    }

    @Override
    public void startServer() {
        server = new Server();

        Network.register(server);

        try {
            // opens a TCP and UDP server
            server.bind(Network.PORT_TCP, Network.PORT_UDP);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }

        server.addListener(new ServerListener());

        WhatAmI.addPlayerToLobby( WhatAmI.getPlayer().getPlayerID(), WhatAmI.getPlayer() );

        WhatAmI.getLobby().print();

        server.start();

        Log.info("MHost-StartServer: " + WhatAmI.getRole());
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

}
