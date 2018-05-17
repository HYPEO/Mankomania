package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the list of player that joined the game,
 * including the host itself.
 * This class is necessary to send the list over the network,
 * to keep the clients update which players joint or left the game lobby.
 */
public class Lobby {

    protected int maxPlayer;
    protected static final int MAX_PLAYER = Network.MAX_PLAYER;

    /**
     * The data structure that holds the players, that are connected.
     *
     * RawPlayer ... data of the player
     * Boolean   ... if the player is ready to participate the game (out of the lobby)
     */
    protected Map<RawPlayer, Boolean> data;

    public Lobby() {
        data = new HashMap<>();
        maxPlayer = MAX_PLAYER;
    }

    /**
     * Creates a new instance from type class Lobby.
     */
    public Lobby(int maxPlayer) {
        this();
        this.maxPlayer = maxPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * @param p value, NetworkPlayer
     */
    public void add(RawPlayer p) {
        data.put(p, false);
    }

    /**
     * Removes a player from the lobby.
     * @param playerID ID to remove
     */
    public void remove(String playerID) {
        Iterator it = data.keySet().iterator();
        while( it.hasNext() ) {
            RawPlayer p = (RawPlayer)it.next();
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
        return data.containsKey(player);
    }

    public RawPlayer contains(String playerId) {
        for( RawPlayer p : data.keySet() ) {
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
        return data.keySet();
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
    public boolean isFull() { return data.size() >= maxPlayer; }

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
        for( RawPlayer rawPlayer : data.keySet() ) {
            Log.info("  " + index + ". ID = '" + rawPlayer +"'");
            index++;
        }
    }

    /**
     * Creates a table representation of the player in the lobby.
     * First row is the head-row.
     * @return
     */
    public List<List<String>> toTable() {

        List<List<String>> table = new ArrayList<>();
        List<String> row;

        /* create header row */
        row = Arrays.asList("Player ID", "Nickname", "IP Address");
        table.add(row);

        /* create data rows */
        for( RawPlayer rp : data.keySet() ) {
            row = Arrays.asList(rp.getPlayerID(), rp.getNickname(), rp.getAddress());
            table.add(row);
        }

        return table;
    }

    /**
     * Returns the value, the ready status, for given player.
     * @param rawPlayer get status for
     * @return ready status for player
     */
    public Boolean getStatus(RawPlayer rawPlayer) {
        return data.get(rawPlayer);
    }

    /**
     * Updates, meaning toggles, the status of the given player.
     * @param rawPlayer RawPlayer toggles status
     */
    public void toggleReadyStatus(RawPlayer rawPlayer) {
        for( Map.Entry<RawPlayer, Boolean> entry : data.entrySet() ) {
            if( entry.getKey().equals(rawPlayer) ) {

                if( entry.getValue() == false ) {
                    entry.setValue(true);
                } else {
                    entry.setValue(false);
                }
            }
        }
    }

    /**
     * Checks if all player within the lobby are ready to start the game.
     * @return boolean
     */
    public boolean areAllPlayerReady() {
        if( this.isEmpty() ) {
            return false;
        }

        for( Boolean isReady : data.values() ) {
            if( ! isReady ) {
                return false;
            }
        }
        return true;
    }
}
