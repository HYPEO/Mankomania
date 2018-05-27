package space.hypeo.mankomania.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;

/**
 * Created by pichlermarc on 27.05.2018.
 */
public class EndGameStage extends Stage {
    private StageManager stageManager;

    public EndGameStage(Viewport viewport, StageManager stageManager, PlayerDetailActor playerDetailActor) {
        super(viewport);
        this.stageManager = stageManager;
        setupBackground();
        playerDetailActor.positionActor(PlayerDetailActor.ScreenPosition.CENTERED);
        this.addActor(playerDetailActor);
    }

    private void setupBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        // Add listener for click on background events.
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(EndGameStage.this);
            }
        });

        this.addActor(background);
    }
}
