package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;

/**
 * Created by pichlermarc on 25.04.2018.
 */

/**
 * Represents a title screen at the beginning of the Game.
 */
public class TitleStage extends Stage {

    StageManager stageManager;

    public TitleStage(StageManager stageManager, Viewport viewport) {
        super(viewport);
        this.stageManager = stageManager;

        // Create actors.
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        Texture titleTexture = new Texture("common/mankomania_logo_shadowed.png");
        titleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image title = new Image(titleTexture);

        Texture tapScreenTexture = new Texture("common/tap_screen.png");
        tapScreenTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image tapScreen = new Image(tapScreenTexture);

        // Set up title.
        title.setWidth(title.getWidth() / 2.5f);
        title.setHeight(title.getHeight() / 2.5f);
        title.setX((viewport.getWorldWidth() / 2) - (title.getWidth() / 2f) + 10f);
        title.setY((viewport.getWorldHeight() * 2f / 3f) - (title.getHeight() / 2f));

        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        // Set up tap-screen message.
        tapScreen.setWidth(tapScreen.getWidth() * 0.5f);
        tapScreen.setHeight(tapScreen.getHeight() * 0.5f);
        tapScreen.setX((viewport.getWorldWidth() / 2f) - (tapScreen.getWidth() / 2f));
        tapScreen.setY((viewport.getWorldHeight() * 1f / 3f) - (title.getHeight() / 2f));

        // Add actors.
        this.addActor(background);
        this.addActor(title);
        this.addActor(tapScreen);

        // Add listener for click events.
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(TitleStage.this);
            }
        });
    }
}
