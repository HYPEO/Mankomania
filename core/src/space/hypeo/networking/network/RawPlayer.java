package space.hypeo.networking.network;


import java.util.UUID;

/**
 * This class represents the raw data or skeleton of a NetworkPlayer.
 * It is necessary, to send a light-weight object through the network.
 */
public class RawPlayer {

    protected String playerID;      // player ID
    protected String nickname;      // nickname

    public RawPlayer() {}

    public RawPlayer(String nickname) {
        this.playerID = generatePlayerID();
        this.nickname = nickname;
    }

    /**
     * Copy constructor.
     * @param p
     */
    public RawPlayer(RawPlayer p) {
        if (p != null && p != this) {
            this.playerID = new String(p.playerID);
            this.nickname = new String(p.nickname);
        }
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getNickname() {
        return nickname;
    }

    /**
     * Gets ID for current player.
     * Take from UUID the last 4 characters.
     * @return String PlayerID
     */
    public static String generatePlayerID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(uuid.toString().length() - 4);
    }

    /**
     * Returns the string representation of current RawPlayer object.
     * @return String representation
     */
    @Override
    public String toString() {
        return "PlayerID: " + playerID + ", Nick: " + nickname;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) { return false; }
        if( o == this) { return true; }

        if( o instanceof RawPlayer ) {
            RawPlayer other = (RawPlayer) o;
            return this.playerID.equals(other.playerID);

        } else if( o instanceof String ) {
            String otherPlayerID = (String) o;
            return this.playerID.equals(otherPlayerID);

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return playerID.hashCode();
    }
}
