package space.hypeo.networking;

import space.hypeo.networking.packages.Player;
import space.hypeo.networking.packages.Lobby;

/**
 * Class Endpoint contains fields,
 * that are common both in host and client
 */
public abstract class Endpoint {

    // The data structure that holds the player-list.
    protected static Lobby lobby;

    // nickname of a player
    protected static String nick;
    // network data of player
    protected static Player player;

    public Endpoint() {
        player = null;
        lobby = new Lobby();
    }
}
