package space.hypeo.networking.packages;

import space.hypeo.networking.network.Player;
import space.hypeo.networking.network.Role;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerDisconnect extends Player {

    public PlayerDisconnect() { super(); }

    public PlayerDisconnect(String playerID, String nick, String address, Role role) {
        super(playerID, nick, address, role);
    }

    public PlayerDisconnect(Player p) {
        super(p);
    }
}
