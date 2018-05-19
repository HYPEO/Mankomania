package space.hypeo.mankomania;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by pichlermarc on 19.05.2018.
 */
public class GameStateManager {

    PlayerActor activePlayer;
    Queue<PlayerActor> playerActors;

    public GameStateManager(Collection<PlayerActor> players)
    {
        playerActors = new LinkedList<>();
        playerActors.addAll(players);
        this.activePlayer = playerActors.remove();
    }

    public void nextPlayer() {
        playerActors.add(activePlayer);
        activePlayer = playerActors.remove();
        //TODO: Set player active.
    }

}
