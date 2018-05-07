package space.hypeo.networking.packages;

public class PingResponse {
    private long time;

    public PingResponse() {}

    public PingResponse(long requestTime) {
        time = requestTime;
    }

    public long getTime() {
        return time;
    }
}
