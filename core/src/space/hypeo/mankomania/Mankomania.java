package space.hypeo.mankomania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import space.hypeo.spriteforce.GameLayerManager;

public class Mankomania extends ApplicationAdapter {
	SpriteBatch spriteBatch;
	GameLayerManager layerManager;
	OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		spriteBatch = new SpriteBatch();
		layerManager = new GameLayerManager();
		layerManager.push(MapFactory.getMapLayer(spriteBatch, layerManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		layerManager.update(Gdx.graphics.getDeltaTime());

		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		layerManager.draw();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
