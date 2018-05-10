package space.hypeo.networking.network;

import java.net.InetAddress;
import java.util.List;

/**
 * Interface provides service methods for player that is game-client
 */
public interface IClientConnector {

    /**
     * Joins the game another host has set up.
     * @param playerID
     * @return
     */
    public boolean joinGame(String playerID);

    /**
     * Starts the client
     */
    public void startClient();

    /**
     * LAN server discovery.
     * broadcast a UDP message on the LAN to discover any servers (hosts) running.
     */
    public List<InetAddress> discoverHosts();

}
