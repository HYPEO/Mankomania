package space.hypeo.networking.packages;

import space.hypeo.networking.network.Player;

public class DisconnectPlayer {
    private Player playerToDisconnect = null;

    public DisconnectPlayer() {}
    public DisconnectPlayer(Player remove) {
        playerToDisconnect = remove;
    }

    public Player getPlayerToDisconnect() {
        return playerToDisconnect;
    }
}
