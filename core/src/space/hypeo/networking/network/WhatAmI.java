package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;

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

    // the local end point of a connection (host or client)
    //private static Object endPoint;

    // role of current player
    private static Role role;

    // network info a player
    private static Player player = new Player();

    // contains a list of all player, that are connected to the host of game
    private static Lobby lobby = new Lobby();

    private static MHost host;
    private static MClient client;

    /*public static Object getEndPoint() {
        return endPoint;
    }*/

    public static void setRole(Role role) {
        // TODO: instantiate host or client after assign role!
        WhatAmI.role = role;
        Log.info("====================");
        Log.info("I'm a " + role);
        Log.info("That are all my available network addresses:");
        Log.info("====================");
        try {
            Log.info( NetworkAddress.getNetworkAddress() );
        } catch (SocketException e) {
            Log.info(e.getMessage());
        }
        Log.info("====================");
    }

    public static Role getRole() {
        return WhatAmI.role;
    }

    public static void setHost() {
        WhatAmI.host = new MHost();
    }

    public static MHost getHost() {
        return WhatAmI.host;
    }

    public static void setClient() {
        WhatAmI.client = new MClient();
    }

    public static MClient getClient() {
        return WhatAmI.client;
    }

    /*public static void setEndPoint(EndPoint endPoint) {
        if( endPoint instanceof Server ) {
            WhatAmI.endpoint = new MHost();
        } else if( endPoint instanceof Client ) {
            WhatAmI.endpoint = new MClient();
        }
    }*/

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        WhatAmI.player = player;
    }

    public static void setLobby(Lobby lobby) {
        WhatAmI.lobby = lobby;
    }

    public static Lobby getLobby() {
        return lobby;
    }

    public static void addPlayerToLobby(String nick, Player player) {
        WhatAmI.lobby.add(nick, player);
    }

    public static void removePlayerFromLobby(String nick) {
        WhatAmI.lobby.remove(nick);
    }

    public static void removePlayerFromLobby(Player player) {
        WhatAmI.lobby.remove(player);
    }

}
