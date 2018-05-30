package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.minlog.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
     * PlayerSkelton ... raw data of the player
     * Boolean       ... if the player is ready to participate the game (out of the lobby)
     */
    protected Map<PlayerSkeleton, Boolean> data;

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
     * @param p value, PlayerSkeleton
     */
    public void add(PlayerSkeleton p) {
        data.put(p, false);
    }

    /**
     * Removes a player from the lobby.
     * @param playerID ID to remove
     */
    public void remove(String playerID) {
        Iterator it = data.keySet().iterator();
        while( it.hasNext() ) {
            PlayerSkeleton p = (PlayerSkeleton) it.next();
            if( p.getPlayerID().equals(playerID) ) {
                it.remove();
            }
        }
    }

    /**
     * Removes a player from the lobby.
     * @param player PlayerSkeleton to remove
     */
    public void remove(PlayerSkeleton player) {
        data.remove(player);
    }

    public boolean contains(PlayerSkeleton player) {
        return data.containsKey(player);
    }

    public PlayerSkeleton contains(String playerId) {
        for( PlayerSkeleton p : data.keySet() ) {
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
    public Set<PlayerSkeleton> getData() {
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
        for( PlayerSkeleton playerSkeleton : data.keySet() ) {
            Log.info("  #" + index + ": '" + playerSkeleton +"'");
            index++;
        }
    }

    /**
     * Returns the value, the ready status, for given player.
     * @param playerSkeleton get status for
     * @return ready status for player
     */
    public Boolean getReadyStatus(PlayerSkeleton playerSkeleton) {
        return data.get(playerSkeleton);
    }

    /**
     * Updates, meaning toggles, the status of the given player.
     * @param playerSkeleton toggles status for this player
     */
    public void toggleReadyStatus(PlayerSkeleton playerSkeleton) {
        Log.info("try to toggle ready status for " + playerSkeleton);
        if( data.containsKey(playerSkeleton) && data.get(playerSkeleton) ) {
            data.put(playerSkeleton, false);
            Log.info("ready status: changed to false");
        } else if( data.containsKey(playerSkeleton) && ! data.get(playerSkeleton) ) {
            data.put(playerSkeleton, true);
            Log.info("ready status: changed to true");
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

    /**
     * Determines the used colors of all players connected to the lobby.
     * @return empty set if nobody has set their color yet.
     *         else: set of used colors.
     */
    public Set<Color> usedColors() {

        Set<Color> usedColorsAllPlayer = new HashSet<>();

        for(PlayerSkeleton playerSkeleton : data.keySet()) {
            Color playerColor = playerSkeleton.getColor();
            if(playerColor != null) {
                usedColorsAllPlayer.add(playerColor);
            }
        }

        return usedColorsAllPlayer;
    }

    /**
     * Updates, sets, the color of the given player.
     * @param playerChangeColor color
     */
    public void setColor(PlayerSkeleton playerChangeColor, Color color) {
        Log.info("set color for " + playerChangeColor);

        for(PlayerSkeleton playerInLobby : data.keySet()) {
            if(playerInLobby.equals(playerChangeColor)) {
                playerInLobby.setColor(color);
            }
        }
    }
}
