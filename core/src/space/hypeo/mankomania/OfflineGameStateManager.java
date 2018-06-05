package space.hypeo.mankomania;

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

    private void nextPlayer() {
        playerActors.add(activePlayer);
        activePlayer.setInactive();
        activePlayer = playerActors.remove();
        activePlayer.setActive();
    }
}
