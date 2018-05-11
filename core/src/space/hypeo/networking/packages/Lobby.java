package space.hypeo.networking.packages;

import com.esotericsoftware.minlog.Log;

import java.util.HashMap;
import java.util.Map;

import space.hypeo.networking.network.Network;
import space.hypeo.networking.network.Player;


/**
 * This class represents the list of player that joined the game,
 * including the host itself.
 * This class is nesessary to send the list over the network,
 * to keep the clients update which players joint or left the game lobby.
 */
public class Lobby {

    /**
     * The data structure that holds the player-list.
     * String     ... PlayerId of the player
     * PlayerInfo ... Network info of the player
     */
    protected Map<String, Player> data;

    public Lobby() {
        data = new HashMap<>();
    }
    public Lobby(Map<String, Player> data) {
        this.data = data;
    }

    public void add(String id, Player p) {
        data.put(id, p);
    }

    public void remove(String id) {
        data.remove(id);
    }

    public void remove(Player p) {
        for( HashMap.Entry<String, Player> entry : data.entrySet() ) {
            if( entry.getValue().equals(p) ) {
                data.remove( entry.getKey() );
            }
        }
    }

    public Map<String, Player> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int size() {
        return data.size();
    }

    public boolean isFull() {return data.size() >= Network.MAX_PLAYER; }

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
            Log.info("  " + index + ". ID = '" + entry.getKey() +"'");
            Log.info("     " + entry.getValue());
            index++;
        }
    }
}
