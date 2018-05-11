package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;
import java.util.UUID;

import space.hypeo.networking.client.MClient;
import space.hypeo.networking.host.MHost;
import space.hypeo.networking.packages.Lobby;

/**
 * This class stores the role of a player in the network:
 * The end point in a network connection could be host or client.
 */
public class WhatAmI {

    // this class is not instantiable!
    private WhatAmI() {}

    // varialbe holds the current player id (compare to primary key, autoincrement)
    //private static int currentPlayerId = 1;

    // role of current player
    private static Role role;

    // network info a player
    private static Player player;

    // contains a list of all player, that are connected to the host of game
    private static Lobby lobby = new Lobby();

    // instances for client and host
    // TODO: better -> one instance as endpoint
    private static MHost host;
    private static MClient client;


    /**
     * Initialize that static class on the device.
     * @param nickname Nickname of the player.
     * @param role Role of the endpoint in the connection.
     */
    public static void init(String nickname, Role role) {

        // TODO: check if WLAN connection is ON and connected to hotspot

        // set Role of current end point
        WhatAmI.role = role;
        Log.info("I'm a " + role);

        // fetch IP in W/LAN
        String currentIp = "";
        try {
            currentIp = NetworkAddress.getNetworkAddress();
            Log.info( "current IP address: " + currentIp );
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }

        // store data of current player
        WhatAmI.player = new Player( generatePlayerID(), nickname, currentIp, role );

        // set role for that endpoint
        if( role == Role.HOST ) {
            WhatAmI.setHost();
        } else if( role == Role.CLIENT ) {
            WhatAmI.setClient();
        }
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
     * Gets the role of the current player.
     * @return Role
     */
    public static Role getRole() {
        return WhatAmI.role;
    }

    /**
     * Registers the endpoint as host connection.
     */
    private static void setHost() {
        WhatAmI.host = new MHost();
    }

    /**
     * Gets the host of the current connection.
     * @return current host if role = HOST, else null
     */
    public static MHost getHost() {
        return WhatAmI.host;
    }

    /**
     * Registers the endpoint as client connection.
     */
    private static void setClient() {
        WhatAmI.client = new MClient();
    }

    /**
     * Gets the client of the current connection.
     * @return current client if role = CLIENT, else null
     */
    public static MClient getClient() {
        return WhatAmI.client;
    }

    /**
     * Gets the network data of the current player.
     * @return player
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Stores the network data of the current player.
     * @param player
     */
    public static void setPlayer(Player player) {
        WhatAmI.player = player;
    }

    /**
     * Resets the network lobby with given lobby.
     * @param lobby connected network player
     */
    public static void setLobby(Lobby lobby) {
        WhatAmI.lobby = lobby;
    }

    /**
     * Gets the lobby, all connected endpoints.
     * @return lobby connected network player
     */
    public static Lobby getLobby() {
        return lobby;
    }

    /**
     * Adds a new network endpoint, a player, to the lobby.
     * @param id player ID
     * @param player network data of player
     */
    public static void addPlayerToLobby(String id, Player player) {
        WhatAmI.lobby.add(id, player);
    }

    /**
     * Removes a given player from the lobby.
     * @param id player ID of endpoint to remove
     */
    public static void removePlayerFromLobby(String id) {
        WhatAmI.lobby.remove(id);
    }

    /**
     * Removes a given player from the lobby.
     * @param player player data of endpoint to remove
     */
    public static void removePlayerFromLobby(Player player) {
        WhatAmI.lobby.remove(player);
    }

}
