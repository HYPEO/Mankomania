package space.hypeo.networking.network;

import com.esotericsoftware.kryonet.Connection;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class Player {

    private String playerID;
    private String nick;
    private String address;

    // TODO: role is redundant in WhatAmI too!
    Role role;

    /**
     * Creates a new instance of PlayerInfo
     * Default Constructor
     */
    public Player() {

        playerID = "";
        nick = "";
        address = "";
        role = Role.NOT_CONNECTED;
    }

    /**
     * Creates a new instance of PlayerInfo
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

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Gets current address
     * @return String IP Address
     */
    public String getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "PlayerID: " + playerID
                + ", Nick: " + nick
                + ", Address: " + address
                + ", Role: " + role;
    }
}
