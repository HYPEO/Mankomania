package space.hypeo.networking.packages;

import com.esotericsoftware.minlog.Log;

import java.util.HashMap;


/**
 * This class represents the list of player that joined the game,
 * including the host itself.
 * This class is nesessary to send the list over the network.
 */
public class Players {

    /**
     * The data structure that holds the player-list.
     * String     ... Nickname of the player
     * PlayerInfo ... Network info of the player
     */
    protected HashMap<String, Player> players;

    public Players() {
        players = new HashMap<String, Player>();
    }

    public void put(String nick, Player p) {
        players.put(nick, p);
    }

    public void remove(String nick) {
        players.remove(nick);
    }

    public void remove(Player p) {
        for( HashMap.Entry<String, Player> entry : players.entrySet() ) {
            if( entry.getValue().equals(p) ) {
                players.remove( entry.getKey() );
            }
        }
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public int size() {
        return players.size();
    }

    public void clear() {
        players.clear();
    }

    public void print() {
        Log.info("HashMap contains:");

        if( players.isEmpty() ) {
            Log.info("NO ENTRIES");
            return;
        }

        int index = 1;
        for( HashMap.Entry<String, Player> entry : players.entrySet() ) {
            Log.info("  " + index + ". Nick = '" + entry.getKey() +"'");
            Log.info("    " + entry.getValue().toString());
            index++;
        }
    }
}
