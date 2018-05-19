package space.hypeo.networking.packages;

import space.hypeo.Player.PlayerManager;
import space.hypeo.networking.network.PlayerSkeleton;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerConnect extends PlayerSkeleton {
    public PlayerConnect(String nickname) {
        super(nickname);
    }

    public PlayerConnect(PlayerSkeleton p) {
        super(p);
    }
}
