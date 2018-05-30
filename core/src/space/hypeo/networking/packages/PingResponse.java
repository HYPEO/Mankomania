package space.hypeo.networking.packages;

public class PingResponse {
    private long time;

    /* NOTE: default constructor is required for network traffic */
    public PingResponse() {}

    public PingResponse(long requestTime) {
        time = requestTime;
    }

    public long getTime() {
        return time;
    }
}
