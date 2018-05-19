package space.hypeo.networking.packages;

import space.hypeo.networking.network.PlayerSkeleton;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerDisconnect extends PlayerSkeleton {

    public PlayerDisconnect(String nickname) {
        super(nickname);
    }

    public PlayerDisconnect(PlayerSkeleton p) {
        super(p);
    }
}
