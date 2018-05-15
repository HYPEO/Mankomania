package space.hypeo.networking.packages;

import space.hypeo.networking.network.RawPlayer;

/**
 * This class is for the handshake between host and client.
 * The host will send an instance of that class if the client connection is accepted.
 */
public class Acknowledge {
    private String msg = "Connection accepted from host ";

    /**
     * Default constructor.
     */
    public Acknowledge() {}

    /**
     * Creates a new instance of Acknowledge with given host IP address.
     * @param host
     */
    public Acknowledge(RawPlayer host) {
        msg += host.getPlayerID();
    }

    @Override
    public String toString() {
        return msg;
    }
}
