package space.hypeo.mankomania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import space.hypeo.spriteforce.GameLayerManager;

public class Mankomania extends ApplicationAdapter {
	SpriteBatch spriteBatch;
	GameLayerManager layerManager;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		GameLayerManager layerManager;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		layerManager.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
