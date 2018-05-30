package space.hypeo.networking.packages;

public class PingRequest {

    private long time;

    /* NOTE: default constructor is required for network traffic */
    public PingRequest() {
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }
}
