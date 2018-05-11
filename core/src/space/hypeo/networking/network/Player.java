package space.hypeo.networking.network;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class Player {

    protected String playerID;  // player ID
    protected String nick;      // nickname
    protected String address;   // IP address in W/LAN

    // The current role in the network connection.
    Role role;

    /**
     * Default Constructor, creates a new instance of Player with empty attributes.
     */
    public Player() {

        playerID = "";
        nick = "";
        address = "";
        role = Role.NOT_CONNECTED;
    }

    /**
     * Creates a new instance of Player.
     * @param playerID
     * @param nick
     * @param address
     * @param role
     */
    public Player(String playerID, String nick,
                  String address,Role role) {
        this.playerID = playerID;
        this.nick = nick;
        this.address = address;
        this.role =role;
    }

    /**
     * Copy constructor, creates a new instance of Player.
     * @param p
     */
    public Player(Player p) {
        if( p != null && p != this) {
            this.playerID = new String(p.playerID);
            this.nick = new String(p.nick);
            this.address = new String(p.address);
            this.role = p.role;
        }
    }

    /**
     * Returns ID of current player.
     * @return String ID.
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Sets the ID of the current player.
     * @param playerID
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Gets the nickname of the current player.
     * @return String nickname.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets the nickname of the current player.
     * @param nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Gets current address.
     * @return String IP Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the role of the current player.
     * @return Role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the string representation of current player.
     * @return String representation
     */
    @Override
    public String toString() {
        return "PlayerID: " + playerID
                + ", Nick: " + nick
                + ", Address: " + address
                + ", Role: " + role;
    }
}
