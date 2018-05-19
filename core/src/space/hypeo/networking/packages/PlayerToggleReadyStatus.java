package space.hypeo.networking.packages;

import space.hypeo.networking.network.PlayerSkeleton;

public class PlayerToggleReadyStatus extends PlayerSkeleton {
    public PlayerToggleReadyStatus(String nickname) {
        super(nickname);
    }

    public PlayerToggleReadyStatus(PlayerSkeleton p) {
        super(p);
    }
}
