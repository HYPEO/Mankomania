package space.hypeo.networking.endpoint;

/**
 * This class provides functionality for an endpoint of a connection.
 */
public interface IEndpoint {

    void stop();

    void broadCastLobby();

    void sendHorseRaceResult(String horseName);
}
