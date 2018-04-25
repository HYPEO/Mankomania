package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;

/**
 * Created by pichlermarc on 25.04.2018.
 */

/**
 * Represents a splash screen at the beginning of the Game.
 */
public class SplashStage extends Stage {

    StageManager stageManager;

    public SplashStage(StageManager stageManager, Viewport viewport)
    {
        super(viewport);
        this.stageManager = stageManager;

        // Create actors.
        Image title = new Image(new Texture("common/mankomania_logo.png"));

        // TODO: Quick and dirty, change this to use world/viewport size.
        title.setWidth(title.getWidth()/2.5f);
        title.setHeight(title.getHeight()/2.5f);
        title.setX((viewport.getWorldWidth()/2) - (title.getWidth()/2f));
        title.setY((viewport.getWorldHeight()*2f/3f) - (title.getHeight()/2f));
        // Add actors.
        this.addActor(title);

        // TODO: figure out whether this is an ugly hack or not...
        SplashStage stage = this;

        // Add listener for click events.
        this.addListener(new ClickListener() {
        public void clicked(InputEvent event, float x, float y) {
            stageManager.remove(stage);
        }
    });
    }

    @Override
    public void draw()
    {
        //TODO: Draw background.
        super.draw();
    }

}
