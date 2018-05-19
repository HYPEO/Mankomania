package space.hypeo.Player;

import space.hypeo.networking.network.PlayerNT;
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

    public PlayerNT getNetworkPlayer(final String nickname) {
        return new PlayerNT(playerManager);
    }
}
