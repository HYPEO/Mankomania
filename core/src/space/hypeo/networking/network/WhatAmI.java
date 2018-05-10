package space.hypeo.networking.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.nio.file.Watchable;

import space.hypeo.networking.client.MClient;
import space.hypeo.networking.host.MHost;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.packages.Player;

/**
 * This class stores the role of a player in the network:
 * The end point in a network connection could be host or client.
 */
public class WhatAmI {

    // the local end point of a connection (host or client)
    //private static Object endPoint;

    // role of current player
    private static CRole role = new CRole(CRole.Role.NOT_CONNECTED);

    // network info a player
    private static Player player = new Player();

    // contains a list of all player, that are connected to the host of game
    private static Lobby lobby = new Lobby();

    private static MHost host;
    private static MClient client;

    /*public static Object getEndPoint() {
        return endPoint;
    }*/

    public static void setRole(CRole.Role r) {
        WhatAmI.role.setRole(r);
    }

    public static CRole getRole() {
        return WhatAmI.player.getCRole();
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

            WhatAmI.client = null;
        } else if( endPoint instanceof Client ) {

            WhatAmI.host = null;
        }
    }*/

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        WhatAmI.player = player;
    }

    public static Lobby getLobby() {
        return lobby;
    }

    public static void setLobby(Lobby lobby) {
        WhatAmI.lobby = lobby;
    }

    public static void addPlayerToLobby(String nick, Player player) {
        WhatAmI.lobby.add(nick, player);
    }

    public static void removePlayerFromLobby(String nick, Player player) {
        WhatAmI.lobby.add(nick, player);
    }


}
