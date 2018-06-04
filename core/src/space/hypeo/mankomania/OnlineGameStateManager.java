package space.hypeo.mankomania;

public class OnlineGameStateManager extends GameStateManager {
    public OnlineGameStateManager(StageManager stageManager, StageFactory stageFactory) {
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
