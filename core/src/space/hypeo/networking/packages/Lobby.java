package space.hypeo.networking.packages;

import com.esotericsoftware.minlog.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import space.hypeo.networking.network.Network;
import space.hypeo.networking.network.NetworkPlayer;


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
    protected Map<String, NetworkPlayer> data;

    /**
     * Creates a new instance from type class Lobby.
     */
    public Lobby() {
        data = new HashMap<>();
    }
    public Lobby(Map<String, NetworkPlayer> data) {
        this.data = new HashMap<>(data);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * @param id key, player ID
     * @param p value, NetworkPlayer
     */
    public void put(String id, NetworkPlayer p) {
        data.put(id, p);
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * @param id key, player ID
     */
    public void remove(String id) {
        data.remove(id);
    }

    /**
     * Removes the mapping for the specified value from this map if present.
     * @param p value, NetworkPlayer
     */
    public void remove(NetworkPlayer p) {
        for( HashMap.Entry<String, NetworkPlayer> entry : data.entrySet() ) {
            if( entry.getValue().equals(p) ) {
                data.remove( entry.getKey() );
            }
        }
    }

    /**
     * Returns the raw data of that instance.
     * @return data HashMap
     */
    public Map<String, NetworkPlayer> getData() {
        return data;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return number of players
     */
    public int size() {
        return data.size();
    }

    /**
     * Retruns true, if maximum number of player has joined the lobby.
     * @return false if new player can join the lobby
     */
    public boolean isFull() {return data.size() >= Network.MAX_PLAYER; }

    /**
     * Removes all of the mappings from this map.
     */
    public void clear() {
        data.clear();
    }

    /**
     * Loggs the string representation of that object to run-console.
     */
    public void log() {
        Log.info("List of player contains:");

        if( data.isEmpty() ) {
            Log.info("NO ENTRIES");
            return;
        }

        int index = 1;
        for( HashMap.Entry<String, NetworkPlayer> entry : data.entrySet() ) {
            Log.info("  " + index + ". ID = '" + entry.getKey() +"'");
            Log.info("     " + entry.getValue());
            index++;
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * @param playerId key in the map
     * @return NetworkPlayer value to given key
     */
    public NetworkPlayer get(String playerId) {
        return data.get(playerId);
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * @return set of player IDs in data
     */
    public Set<String> keySet() {
        return data.keySet();
    }

    /**
     * Returns a Collection view of the values contained in this map.
     * @return collection of NetworkPlayer in data
     */
    public Collection<NetworkPlayer> values() {
        return data.values();
    }
}
