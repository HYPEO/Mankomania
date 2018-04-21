package space.hypeo.mankomania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * The GDX Game class, called from the android project.
 */
public class Mankomania extends ApplicationAdapter {
    Stage currentStage;

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,480, 800);
        ExtendViewport viewport = new ExtendViewport(480, 800, camera);
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
