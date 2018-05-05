package space.hypeo.networking.packages;

public class PingRequest {

    private long time;

    public PingRequest() {
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }
}
