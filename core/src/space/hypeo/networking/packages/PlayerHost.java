package space.hypeo.networking.packages;

import space.hypeo.networking.network.RawPlayer;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerHost extends RawPlayer {

    public PlayerHost() {
        super();
    }

    public PlayerHost(RawPlayer p) {
        super(p);
    }
}
