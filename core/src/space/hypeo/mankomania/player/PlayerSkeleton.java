package space.hypeo.mankomania.player;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;
import java.util.UUID;

import space.hypeo.networking.network.NetworkAddress;

public class PlayerSkeleton {
    protected String playerID;      // player ID
    protected String nickname;      // nickname
    protected String address;       // IP address in W/LAN

    /* NOTE: default constructor required for network traffic */
    public PlayerSkeleton() {}

    public PlayerSkeleton(String nickname) {
        this.nickname = nickname;

        this.playerID = generatePlayerID();

        // fetch IP in W/LAN
        String currentIpAddr = "";
        try {
            currentIpAddr = NetworkAddress.getNetworkAddress();
            Log.info( "current IP address: " + currentIpAddr );
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }
        this.address = currentIpAddr;
    }

    /**
     * Copy constructor.
     * @param playerSkeleton the new instance will be a deep copy of that object
     */
    public PlayerSkeleton(PlayerSkeleton playerSkeleton) {
        if (playerSkeleton != null && playerSkeleton != this) {
            this.playerID = playerSkeleton.playerID;
            this.nickname = playerSkeleton.nickname;
            this.address = playerSkeleton.address;
        }
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Gets ID for current player.
     * Take from UUID the last 4 characters.
     * @return String PlayerID
     */
    private static String generatePlayerID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(uuid.toString().length() - 4);
    }

    /**
     * Returns the string representation of current PlayerBusiness object.
     * @return String representation
     */
    @Override
    public String toString() {
        return "ID " + playerID + ", nick " + nickname + ", addr " + address;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) { return false; }
        if( o == this) { return true; }

        if( o instanceof PlayerSkeleton) {
            PlayerSkeleton other = (PlayerSkeleton) o;
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