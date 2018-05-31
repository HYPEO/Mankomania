package space.hypeo.networking.endpoint;

/**
 * This class provides functionality for an endpoint of a connection.
 */
public interface IEndpoint {

    public void stop();

    /**
     * Resends a received MoneyAmount from player to another player.
     * @param playerId player to change balance
     * @param balance total amount of player balance
     */
    public void changeBalance(String playerId, int balance);

    public void broadCastLobby();
}
