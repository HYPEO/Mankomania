package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;
import java.util.UUID;

import space.hypeo.mankomania.StageManager;
import space.hypeo.networking.endpoint.Endpoint;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.networking.packages.Lobby;

/**
 * This class is a global auxiliary class that provides different references among the game.
 */
public final class WhatAmI {

    // this class is not instantiable!
    private WhatAmI() {}

    // network info a player
    private static Player player = null;

    // contains a list of all player, that are connected to the host of game
    private static Lobby lobby = new Lobby();

    // reference to client or host
    private static Endpoint endpoint = null;

    private static StageManager stageManager = null;

    private static boolean gameRunning = false;

    /**
     * Initialize that static class on the device.
     * @param nickname Nickname of the player.
     * @param role Role of the endpoint in the connection.
     */
    public static void init(String nickname, Role role, StageManager sm) {

        // TODO: check if WLAN connection is ON and connected to hotspot

        if( endpoint != null ) {
            Log.warn("init: There is already an open connection!");
            return;
        }

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

        // sets endpoint + start process
        setEndpoint(role);

        WhatAmI.stageManager = sm;
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
        if( endpoint == null ) {
            return Role.NOT_CONNECTED;
        }
        return endpoint.getRole();
    }

    /**
     * Sets the endpoint by given role and starts process.
     * @param role HOST or CLIENT
     */
    private static void setEndpoint(Role role) {

        if( role == Role.HOST ) {
            endpoint = new MHost();
            endpoint.start();
        } else if( role == Role.CLIENT ) {
            endpoint = new MClient();
            endpoint.start();
        } else {
            Log.info("Enpoint could not be initialized for given Role: " + role);
        }
    }

    public static Endpoint getEndpoint() {
        return endpoint;
    }

    public static void stopEndpoint() {

        if( endpoint != null ) {
            endpoint.stop();
        } else {
            Log.info("No process running - nothing to do.");
        }
    }

    public static void closeEndpoint() {

        if( endpoint != null ) {
            endpoint.close();
            endpoint = null;
        } else {
            Log.info("No process running - nothing to do.");
        }
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
        WhatAmI.lobby.put(id, player);
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

    /**
     * Gets the reference to the StageManager.
     * @return stageManager
     */
    public static StageManager getStageManager() {
        return stageManager;
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }
}
