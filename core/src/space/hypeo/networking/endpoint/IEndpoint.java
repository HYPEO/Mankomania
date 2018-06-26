package space.hypeo.networking.endpoint;

/**
 * This class provides functionality for an endpoint of a connection.
 */
public interface IEndpoint {

    /**
     * Stops the endpoint.
     * Endpoint can connect later again w/o start.
     */
    void stop();

    /**
     * Closes the endpoint.
     * Endpoint has to be restarted before connecting again.
     */
    void close();

    /**
     * Sends the lobby broadcast over the network.
     */
    void broadCastLobby();

    /**
     * Sends the name of the winning horse over the network.
     * @param horseName name of the winning horse.
     */
    void sendHorseRaceResult(String horseName);

    /**
     * Sends the id of the winning slot over the network.
     * @param slotId number of the winning slot.
     */
    void sendRouletteResult(int slotId);

    /**
     * Disconnects the current endpoint from the network.
     */
    void disconnect();
}
