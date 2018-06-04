package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.minlog.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.Network;

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
     * PlayerId      ... identifies the player in the game
     * PlayerSkelton ... raw data of the player
     */
    protected Map<String, PlayerSkeleton> data;

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
     * @param playerId ID of the player
     * @param p value, PlayerSkeleton
     */
    public void put(String playerId, PlayerSkeleton p) {
        Log.info("Lobby contains " + data.size() + " player.");
        Log.info("Insert or update ID '" + playerId + "'");
        data.put(playerId, p);
        Log.info("Now Lobby contains " + data.size() + " player.");
    }

    public PlayerSkeleton get(String playerId) {
        return data.get(playerId);
    }

    /**
     * Removes a player from the lobby.
     * @param playerID ID to remove
     */
    public void remove(String playerID) {
        data.remove(playerID);
    }

    /**
     * Removes a player from the lobby.
     * @param player PlayerSkeleton to remove
     */
    public void remove(PlayerSkeleton player) {
        data.remove(player);
    }

    public boolean contains(String playerId) {
        return data.containsKey(playerId);
    }

    public boolean contains(PlayerSkeleton player) {
        return data.containsValue(player);
    }

    /**
     * Returns the raw data of that instance.
     * @return data Set
     */
    public Set<String> keySet() {
        return data.keySet();
    }

    public Collection<PlayerSkeleton> values() { return data.values(); }

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
     * Checks if all player within the lobby are ready to start the game.
     * @return boolean
     */
    public boolean areAllPlayerReady() {
        if( this.isEmpty() ) {
            return false;
        }

        for(PlayerSkeleton playerInLobby : data.values() ) {
            if(!playerInLobby.isReady()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all player within the lobby have already set their color.
     * @return boolean
     */
    public boolean areAllPlayerColored() {
        if( this.isEmpty() ) {
            return false;
        }

        for(PlayerSkeleton playerInLobby : data.values() ) {
            if(playerInLobby.getColor() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines the used colors of all players connected to the lobby.
     * @return empty set if nobody has set their color yet.
     *         else: set of used colors.
     */
    public Set<Color> usedColors() {
        Set<Color> usedColorsAllPlayer = new HashSet<>();

        for(PlayerSkeleton playerSkeleton : data.values()) {
            Color playerColor = playerSkeleton.getColor();
            if(playerColor != null) {
                usedColorsAllPlayer.add(playerColor);
            }
        }

        return usedColorsAllPlayer;
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
        for( PlayerSkeleton playerSkeleton : data.values() ) {
            Log.info("  #" + index + ": '" + playerSkeleton +"'");
            index++;
        }
    }
}
