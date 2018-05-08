package space.hypeo.networking;

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
    protected HashMap<String, PlayerInfo> players;

    public Players() {
        players = new HashMap<String, PlayerInfo>();
    }

    public void put(String nick, PlayerInfo pInfo) {
        players.put(nick, pInfo);
    }

    public void remove(String nick) {
        players.remove(nick);
    }

    public void remove(PlayerInfo pInfo) {
        for( HashMap.Entry<String, PlayerInfo> entry : players.entrySet() ) {
            if( entry.getValue().equals(pInfo) ) {
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
        for( HashMap.Entry<String, PlayerInfo> entry : players.entrySet() ) {
            Log.info("  " + index + ". Nick = '" + entry.getKey() +"'");
            Log.info("    " + entry.getValue().toString());
            index++;
        }
    }
}
