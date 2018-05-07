package space.hypeo.networking.host;

import java.io.IOException;
import java.util.HashMap;

import space.hypeo.networking.IHostConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.PlayerInfo;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class MHost implements IPlayerConnector, IHostConnector {

    private com.esotericsoftware.kryonet.Server server;

    /**
     * The data structure that holds the player-list.
     * String     ... Nickname of the player
     * PlayerInfo ... Network info of the player
     */
    private HashMap<String, PlayerInfo> players;

    /**
     * Constructs instance of class MHost
     */
    public MHost() {
        players = new HashMap<String, PlayerInfo>();
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
                return;
            }

            PlayerInfo newPlayer = new PlayerInfo(connection, Network.Role.client);

            players.put(newPlayer.getAddress(), newPlayer);
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

        // TODO: add server itself in players
        //PlayerInfo self = new PlayerInfo();
        //server.getKryo().

        // TODO: create a lobby: see each player in players
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
    public HashMap<String, PlayerInfo> registeredPlayers() {
        return players;
    }
}
