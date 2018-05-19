package space.hypeo.networking.network;

import space.hypeo.Player.PlayerManager;

/**
 * This class represents the raw data or skeleton of a PlayerNT.
 * It is necessary, to send a light-weight object through the network.
 */
public class PlayerBusiness extends PlayerSkeleton {
    final PlayerManager playerManager;

    public PlayerBusiness(String nickname, final PlayerManager playerManager) {
        super(nickname);
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerSkeleton getPlayerSkeleton() {
        return new PlayerSkeleton(this);
    }
}
