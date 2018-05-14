package space.hypeo.networking.packages;

import space.hypeo.networking.network.RawPlayer;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerConnect extends RawPlayer {

    public PlayerConnect() {
        super();
    }

    public PlayerConnect(RawPlayer p) {
        super(p);
    }
}
