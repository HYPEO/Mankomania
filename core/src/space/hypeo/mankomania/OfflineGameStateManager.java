package space.hypeo.mankomania;

import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by pichlermarc on 20.05.2018.
 */
public class OfflineGameStateManager extends GameStateManager{

    public OfflineGameStateManager(StageManager stageManager, StageFactory stageFactory) {
        super(stageManager, stageFactory);
    }

    @Override
    public void endTurn() {
        nextPlayer();
    }

    @Override
    public void updatePlayer(PlayerActor playerActor) {
        // do nothing, player already updated offline.
    }

    private void nextPlayer() {
        playerActors.add(activePlayer);
        activePlayer.setInactive();
        activePlayer = playerActors.remove();
        activePlayer.setActive();
    }

    @Override
    public void startGame(){
        // do nothing, game already started...
    }
}
