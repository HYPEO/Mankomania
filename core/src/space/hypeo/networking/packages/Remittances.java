package space.hypeo.networking.packages;

public class Remittances {

    private int amount;
    private String senderId;
    private String receiverId;

    /* NOTE: default constructor is required for network traffic */
    public Remittances() {}

    public Remittances(String senderId, String receiverId, int amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public int getMoneyAmount() {
        return amount;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    @Override
    public String toString() {
        return "From " + senderId + ", to " + receiverId + ", amount " + amount;
    }
}
