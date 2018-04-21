package space.hypeo.mankomania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Mankomania extends ApplicationAdapter {
    Stage currentStage;

    @Override
    public void create() {
        StretchViewport viewport = new StretchViewport(480, 800);
        currentStage = StageFactory.getMapStage(viewport);
        Gdx.input.setInputProcessor(currentStage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentStage.act();
        currentStage.draw();
    }
}
