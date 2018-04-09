package space.hypeo.networking;

import java.util.List;

public class HostConnector implements IPlayerConnector, IHostConnector {

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
    public void advertiseGame() {

    }

    @Override
    public boolean startGame() {
        return false;
    }

}
