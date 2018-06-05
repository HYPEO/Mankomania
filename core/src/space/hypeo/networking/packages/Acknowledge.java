package space.hypeo.networking.packages;

import space.hypeo.mankomania.player.PlayerSkeleton;

/**
 * This class is for the handshake between host and client.
 * The host will send an instance of that class if the client connection is accepted.
 */
public class Acknowledge {
    private String msg = "Connection accepted from host ";

    /* NOTE: default constructor is required for network traffic */
    public Acknowledge() {}

    /**
     * Creates a new instance of Acknowledge with given host IP address.
     * @param host
     */
    public Acknowledge(PlayerSkeleton host) {
        msg += host.getPlayerID();
    }

    @Override
    public String toString() {
        return msg;
    }
}
