package space.hypeo.networking.packages;

import space.hypeo.networking.network.Player;
import space.hypeo.networking.network.Role;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerConnect extends Player {

    public PlayerConnect() {
        super();
    }

    public PlayerConnect(String playerID, String nick, String address, Role role) {
        super(playerID, nick, address, role);
    }

    public PlayerConnect(Player p) {
        super(p);
    }
}
