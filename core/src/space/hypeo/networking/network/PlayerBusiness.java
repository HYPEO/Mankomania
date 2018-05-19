package space.hypeo.networking.network;

import space.hypeo.player.PlayerManager;

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerSkeleton getPlayerSkeleton() {
        return new PlayerSkeleton(this);
    }
}
