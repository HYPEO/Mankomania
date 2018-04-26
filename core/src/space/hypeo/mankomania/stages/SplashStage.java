package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.w3c.dom.css.Rect;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.RectangleActor;

/**
 * Created by pichlermarc on 25.04.2018.
 */

/**
 * Represents a splash screen at the beginning of the Game.
 */
public class SplashStage extends Stage {

    StageManager stageManager;

    public SplashStage(StageManager stageManager, Viewport viewport) {
        super(viewport);
        this.stageManager = stageManager;

        // Create actors.
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        Image title = new Image(new Texture("common/mankomania_logo.png"));

        title.setWidth(title.getWidth() / 2.5f);
        title.setHeight(title.getHeight() / 2.5f);
        title.setX((viewport.getWorldWidth() / 2) - (title.getWidth() / 2f));
        title.setY((viewport.getWorldHeight() * 2f / 3f) - (title.getHeight() / 2f));

        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add actors.
        this.addActor(background);
        this.addActor(title);

        // Add listener for click events.
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(SplashStage.this);
            }
        });
    }
}
