package space.hypeo.networking.packages;

public class Notification {

    private String msg;

    /* NOTE: default constructor is required for network traffic */
    public Notification() {
        msg = null;
    }

    public Notification(String message) {
        msg = message;
    }

    @Override
    public String toString() {
        return msg;
    }
}
