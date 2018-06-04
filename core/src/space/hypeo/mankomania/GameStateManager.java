package space.hypeo.mankomania;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import space.hypeo.mankomania.actors.player.PlayerActor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by pichlermarc on 19.05.2018.
 */
public abstract class GameStateManager {

    PlayerActor activePlayer;
    Queue<PlayerActor> playerActors;
    protected StageManager stageManager;
    protected StageFactory stageFactory;

    public GameStateManager(StageManager stageManager, StageFactory stageFactory)
    {
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActors = new LinkedList<>();
    }

    public void addPlayer(PlayerActor playerActor)
    {
        if(this.registeredPlayerCount() < 1) {
            this.activePlayer = playerActor;
            this.activePlayer.setActive();
        }
        else {
            this.playerActors.add(playerActor);
        }
    }

    public abstract void endTurn();

    public void setWinner(PlayerActor playerActor)
    {
        stageManager.remove(stageManager.getCurrentStage());
        stageManager.push(stageFactory.getEndGameStage(playerActor));
    }

    public Collection<PlayerActor> getPlayers(){
        LinkedList<PlayerActor> actors = new LinkedList<>();
        if(activePlayer != null)
            actors.add(activePlayer);
        actors.addAll(playerActors);
        return actors;
    }

    public int registeredPlayerCount()
    {
        if(activePlayer == null)
            return 0;
        else
            return playerActors.size()+1;
    }
}
