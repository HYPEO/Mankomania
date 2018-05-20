package space.hypeo.networking.packages;

import space.hypeo.mankomania.player.PlayerSkeleton;

public class PlayerToggleReadyStatus extends PlayerSkeleton {

    /* NOTE: default constructor is required for network traffic */
    public PlayerToggleReadyStatus() {
        super();
    }

    public PlayerToggleReadyStatus(String nickname) {
        super(nickname);
    }

    public PlayerToggleReadyStatus(PlayerSkeleton p) {
        super(p);
    }
}
