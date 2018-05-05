package space.hypeo.networking.host;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import space.hypeo.networking.IHostConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class MHost implements IPlayerConnector, IHostConnector {

    private com.esotericsoftware.kryonet.Server server;
    private HashMap<String, String> players;

    private class ServerListener extends Listener {

        /**
         * If connected
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);
            connection.getRemoteAddressTCP().getHostName();
            connection.getRemoteAddressTCP().getAddress();
            connection.getRemoteAddressTCP().getPort();

        }

        /**
         * If disconnected
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
        }

        /**
         * If object got received
         * @param connection
         * @param object
         */
        @Override
        public void received(Connection connection, Object object) {
            super.received(connection, object);

            if( object instanceof PingRequest ) {
                PingResponse pingResponse = new PingResponse();
                connection.sendTCP(pingResponse);
            }
        }
    }

    @Override
    public void advertiseGame() {

    }

    @Override
    public boolean startGame() {
        return false;
    }

    @Override
    public void startServer() {
        server = new Server();
        server.start();

        try {
            server.bind(25454);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new ServerListener());

        Kryo kryo = server.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
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
    public HashMap<String, String> registeredPlayers() {
        return players;
    }
}
