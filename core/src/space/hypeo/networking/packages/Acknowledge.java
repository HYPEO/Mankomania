package space.hypeo.networking.packages;

/**
 * This class is for the handshake between host and client.
 * The host will send an instance of that class if the client connection is accepted.
 */
public class Acknowledge {
    private String msg = "Connection accepted from host ";

    public Acknowledge() {}

    public Acknowledge(String hostIp) {
        msg += hostIp;
    }

    @Override
    public String toString() {
        return msg;
    }
}
