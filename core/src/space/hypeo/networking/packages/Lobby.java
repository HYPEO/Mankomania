package space.hypeo.networking.packages;

import com.esotericsoftware.minlog.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import space.hypeo.networking.network.Network;
import space.hypeo.networking.network.RawPlayer;


/**
 * This class represents the list of player that joined the game,
 * including the host itself.
 * This class is necessary to send the list over the network,
 * to keep the clients update which players joint or left the game lobby.
 */
public class Lobby {

    /**
     * The data structure that holds the players, that are connected.
     */
    protected Set<RawPlayer> data;

    /**
     * Creates a new instance from type class Lobby.
     */
    public Lobby() {
        data = new HashSet<>();
    }
    /*public Lobby(Set<RawPlayer> data) {
        this.data = new HashSet<>(data);
    }*/

    /**
     * Associates the specified value with the specified key in this map.
     * @param p value, NetworkPlayer
     */
    public void add(RawPlayer p) {
        data.add(p);
    }

    /**
     * Removes a player from the lobby.
     * @param playerID ID to remove
     */
    public void remove(String playerID) {
        Iterator<RawPlayer> it = data.iterator();
        while( it.hasNext() ) {
            RawPlayer p = it.next();
            if( p.getPlayerID().equals(playerID) ) {
                it.remove();
            }
        }
    }

    /**
     * Removes a player from the lobby.
     * @param player RawPlayer to remove
     */
    public void remove(RawPlayer player) {
        data.remove(player);
    }

    public boolean contains(RawPlayer player) {
        return data.contains(player);
    }

    public RawPlayer contains(String playerId) {
        for( RawPlayer p : data ) {
            if( p.getPlayerID().equals(playerId) ) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns the raw data of that instance.
     * @return data Set
     */
    public Set<RawPlayer> getData() {
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
    public boolean isFull() { return data.size() >= Network.MAX_PLAYER; }

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
        for( RawPlayer rawPlayer : data ) {
            Log.info("  " + index + ". ID = '" + rawPlayer +"'");
            index++;
        }
    }
}
