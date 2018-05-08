package space.hypeo.networking;

import space.hypeo.networking.packages.Players;

/**
 * Class Endpoint contains fields,
 * that are common both in host and client
 */
public abstract class Endpoint {

    // TODO: implement HashMap as new class and register it for host/client

    /**
     * The data structure that holds the player-list.
     * String     ... Nickname of the player
     * PlayerInfo ... Network info of the player
     */
    protected Players players;

    public Endpoint() {
        players = new Players();
    }
}
