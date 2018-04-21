package space.hypeo.mankomania;

import java.util.List;

import space.hypeo.mankomania.behaviour.PlayerBehaviour;

/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * Holds the Game's current state.
 */
public class GameState {
    private List<PlayerBehaviour> players;

    /**
     * List of players that play the game.
     *
     * @param players
     */
    public GameState(List<PlayerBehaviour> players) {
        this.players = players;
    }
}
