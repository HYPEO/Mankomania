package space.hypeo.player;

import space.hypeo.networking.network.PlayerBusiness;

public class PlayerFactory {
    private final PlayerManager playerManager;

    public PlayerFactory(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerBusiness getPlayerBusiness(final String nickname) {
        return new PlayerBusiness(nickname, playerManager);
    }
}
