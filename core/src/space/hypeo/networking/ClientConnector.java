package space.hypeo.networking;

import java.util.List;

/**
 * Implementation of ClientService as Singleton
 */
public class ClientConnector implements IPlayerConnector, IClientConnector {

    private static ClientConnector instance;

    private ClientConnector() {}

    public static ClientConnector getInstance() {
        if( instance == null ) {
            instance = new ClientConnector();
        }

        return instance;
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
    public List<String> registeredPlayers() {
        return null;
    }

    @Override
    public boolean joinGame(String playerID) {
        return false;
    }

}
