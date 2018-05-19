package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.networking.network.PlayerNT;

/**
 * Created by pichlermarc on 25.04.2018.
 */

/**
 * Represents a title screen at the beginning of the Game.
 */
public class TitleStage extends Stage {
    private Image title;
    private PlayerNT playerNT;

    public TitleStage(final StageManager stageManager, Viewport viewport) {
        super(viewport);

        setUpBackground();
        setUpTitle();
        setUpTapScreen();

        // Add listener for click events.
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(TitleStage.this);
            }
        });
    }

    private void setUpTapScreen()
    {
        Texture tapScreenTexture = new Texture("common/tap_screen.png");
        tapScreenTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image tapScreen = new Image(tapScreenTexture);

        // Set up tap-screen message.
        tapScreen.setWidth(tapScreen.getWidth() * 0.5f);
        tapScreen.setHeight(tapScreen.getHeight() * 0.5f);
        tapScreen.setX((this.getViewport().getWorldWidth() / 2f) - (tapScreen.getWidth() / 2f));
        tapScreen.setY((this.getViewport().getWorldHeight() * 1f / 3f) - (title.getHeight() / 2f));

        this.addActor(tapScreen);
    }

    private void setUpTitle()
    {
        Texture titleTexture = new Texture("common/mankomania_logo_shadowed.png");
        titleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title = new Image(titleTexture);

        // Set up title.
        title.setWidth(title.getWidth() / 2.5f);
        title.setHeight(title.getHeight() / 2.5f);
        title.setX((this.getViewport().getWorldWidth() / 2) - (title.getWidth() / 2f) + 10f);
        title.setY((this.getViewport().getWorldHeight() * 2f / 3f) - (title.getHeight() / 2f));

        this.addActor(title);
    }

    private void setUpBackground()
    {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }
}
