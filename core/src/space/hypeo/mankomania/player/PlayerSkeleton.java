package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.minlog.Log;

import java.net.SocketException;
import java.util.UUID;

import space.hypeo.networking.network.NetworkAddress;

/**
 * This class represents the "raw-player" of a player (on a device).
 */
public class PlayerSkeleton {
    protected String playerID;      // player ID
    protected String nickname;      // nickname
    protected String address;       // IP address in W/LAN

    protected boolean isReady;      // ready to start the game
    protected Color color;          // color on the map (unique)
    protected boolean isActive;     // true when it is my turn
    protected int balance;          // total amount of money

    protected float xImagePosition;
    protected float yImagePosition;

    /* NOTE: default constructor required for network traffic */
    public PlayerSkeleton() {}

    /**
     * Creates instance of PlayerSkeleton.
     * @param nickname
     */
    public PlayerSkeleton(String nickname) {
        this.nickname = nickname;

        playerID = generatePlayerID();

        // fetch IP in W/LAN
        String currentIpAddr = "";
        try {
            currentIpAddr = NetworkAddress.getNetworkAddress();
            Log.info( "current IP address: " + currentIpAddr );
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }
        address = currentIpAddr;

        isReady = false;
        color = null;
        isActive = false;
        balance = PlayerFactory.START_BALANCE;

        xImagePosition = 0;
        yImagePosition = 0;

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
            this.isActive = playerSkeleton.isActive;
            this.balance = playerSkeleton.balance;
        }
    }

    /**
     * Gets ID of current player.
     * @return
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Gets nickname of current player.
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets IP address of current player (in WLAN).
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Is current player ready to start the game?
     * @return
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Sets the current player ready to start the game.
     * @param ready
     */
    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * Gets the color of the current player.
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the current player.
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
        Log.info("set my (" + nickname + ") color to " + color);
    }

    /**
     * Is current player active means is current player's turn?
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets current player active means it's its turn now.
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets balance (total mony amount) of current player.
     * @return
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets balance (total mony amount) of current player.
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public float getxImagePosition() {
        return xImagePosition;
    }

    public void setxImagePosition(float xImagePosition) {
        this.xImagePosition = xImagePosition;
    }

    public float getyImagePosition() {
        return yImagePosition;
    }

    public void setyImagePosition(float yImagePosition) {
        this.yImagePosition = yImagePosition;
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
        return "ID " + playerID + ", nick " + nickname + ", addr " + address + ", " +
                (isReady ? " " : " Not ") + "Ready, " +
                (color != null ? color : "<no color>") + ", " +
                (isActive ? "active" : "inactive") +
                ", balance " + balance + "€";
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
