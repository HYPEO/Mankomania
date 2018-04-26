package space.hypeo.mankomania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * The GDX Game class, called from the android project.
 */
public class Mankomania extends ApplicationAdapter {
    private StageManager manager;

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,480, 800);
        ExtendViewport viewport = new ExtendViewport(480, 800, camera);
        manager = new StageManager();
        manager.push(StageFactory.getMainMenu(viewport, manager));
        manager.push(StageFactory.getTitleStage(viewport, manager));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        manager.getCurrentStage().act();
        manager.getCurrentStage().draw();
    }
}
