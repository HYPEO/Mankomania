package space.hypeo.networking;

import space.hypeo.networking.packages.Player;
import space.hypeo.networking.packages.Players;

/**
 * Class Endpoint contains fields,
 * that are common both in host and client
 */
public abstract class Endpoint {

    // The data structure that holds the player-list.
    protected Players players;

    // nickname of a player
    protected String nick;
    // network data of player
    protected Player player;

    public Endpoint() {
        player = null;
        players = new Players();
    }
}
