package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import space.hypeo.spriteforce.GameLayer;
import space.hypeo.spriteforce.GameLayerManager;
import space.hypeo.spriteforce.GameObject;

/**
 * Created by pichlermarc on 06.04.2018.
 */

public class GdxGameLayer extends GameLayer {

    private SpriteBatch spriteBatch;

    /**
     * Creates a new GameLayer for use with libGDX
     *
     * @param layerManager The required layerManager
     * @param spriteBatch  SpriteBatch used for rendering associated GameObjects.
     */
    public GdxGameLayer(GameLayerManager layerManager, SpriteBatch spriteBatch) {
        super(layerManager);
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void draw() {
        spriteBatch.begin();
        for (GameObject gameObject : this.gameObjects) {
            gameObject.draw();
        }
        spriteBatch.end();
    }

    @Override
    public GameObject createGameObject(String texture) {
        GameObject gameObject = new GdxGameObject(spriteBatch, texture);
        this.addGameObject(gameObject);
        return gameObject;
    }
}
