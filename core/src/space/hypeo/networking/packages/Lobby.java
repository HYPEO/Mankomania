package space.hypeo.networking.packages;

import com.esotericsoftware.minlog.Log;

import java.util.HashMap;

import space.hypeo.networking.network.Network;


/**
 * This class represents the list of player that joined the game,
 * including the host itself.
 * This class is nesessary to send the list over the network,
 * to keep the clients update which players joint or left the game lobby.
 */
public class Lobby {

    /**
     * The data structure that holds the player-list.
     * String     ... Nickname of the player
     * PlayerInfo ... Network info of the player
     */
    protected HashMap<String, Player> data;

    public Lobby() {
        data = new HashMap<String, Player>();
    }

    public void add(String nick, Player p) {
        data.put(nick, p);
    }

    public void remove(String nick) {
        data.remove(nick);
    }

    public void remove(Player p) {
        for( HashMap.Entry<String, Player> entry : data.entrySet() ) {
            if( entry.getValue().equals(p) ) {
                data.remove( entry.getKey() );
            }
        }
    }

    public HashMap<String, Player> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int size() {
        return data.size();
    }

    public boolean ifFull() {return data.size() >= Network.MAX_PLAYER; }

    public void clear() {
        data.clear();
    }

    public void print() {
        Log.info("List of player contains:");

        if( data.isEmpty() ) {
            Log.info("NO ENTRIES");
            return;
        }

        int index = 1;
        for( HashMap.Entry<String, Player> entry : data.entrySet() ) {
            Log.info("  " + index + ". Nick = '" + entry.getKey() +"'");
            Log.info("     " + entry.getValue().toString());
            index++;
        }
    }
}
