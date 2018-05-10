package space.hypeo.networking.host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import space.hypeo.networking.network.IHostConnector;
import space.hypeo.networking.network.IPlayerConnector;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.networking.network.CRole;
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
                connection.sendTCP(new Notification("Sorry, no more space for additional player left"));
                return;
            }

            Player newPlayer = new Player(connection, CRole.Role.CLIENT);
            Log.info("Added new Client with: " + newPlayer.toString());

            // TODO: get the "real" nick of recently connected player
            WhatAmI.getLobby().add(newPlayer.getAddress(), newPlayer);
            connection.sendTCP(new Notification("You are connected ..."));

            WhatAmI.getLobby().print();
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

            Player leavingPlayer = new Player(connection, CRole.Role.CLIENT);

            WhatAmI.getLobby().remove(leavingPlayer);

            WhatAmI.getLobby().print();
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
        WhatAmI.getLobby().clear();
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

        String playerId = "00";
        String nick = "the_mighty_host";

        String selfAddress = "";
        try {
            selfAddress = InetAddress.getLocalHost().toString();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }

        WhatAmI.setPlayer(
                new Player(playerId, nick,
                "/" + selfAddress, selfAddress, Network.PORT_TCP,
                CRole.Role.HOST)
        );

        WhatAmI.addPlayerToLobby( nick, WhatAmI.getPlayer() );

        WhatAmI.getLobby().print();

        Log.info("MHost-StartServer: " + WhatAmI.getRole().toString());
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
