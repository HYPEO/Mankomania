package space.hypeo.mankomania;

import java.util.List;
import space.hypeo.mankomania.actors.PlayerActor;

/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * Holds the Game's current state.
 */
public class GameState {
    private List<PlayerActor> players;

    /**
     * List of players that play the game.
     *
     * @param players
     */
    public GameState(List<PlayerActor> players) {
        this.players = players;
    }
}
