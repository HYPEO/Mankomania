package space.hypeo.spriteforce;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pichlermarc on 06.04.2018.
 */

/**
 * The GameLayer Class holds a list of GameObjects, that represent a Unit, i.e. a window, or menu.
 */
public abstract class GameLayer {
    protected Collection<GameObject> gameObjects;
    protected GameLayerManager layerManager;

    /**
     * Creates a new instance of the GameLayer class.
     *
     * @param layerManager The layer this
     */
    public GameLayer(GameLayerManager layerManager) {
        this.gameObjects = new LinkedList<GameObject>();
        this.layerManager = layerManager;
    }

    /**
     * Updates gameObjects
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }
    }

    /**
     * Draws all the objects on this layer.
     */
    public abstract void draw();

    /**
     * Adds a GameObject to be drawn on this layer.
     *
     * @param gameObject
     */
    public void addGameObject(GameObject gameObject) {
        gameObject.setParent(this);
        gameObjects.add(gameObject);
    }

    /**
     * Removes a GameObject form this layer.
     *
     * @param gameObject
     */
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObject.setParent(null);
    }

    /**
     * Defocuses this Layer.
     */
    public void defocus() {
        layerManager.defocus(this);
    }

    /**
     * Creates a GameObject on this Layer.
     *
     * @param texture The texture of the GameObject.
     * @return The created GameObject.
     */
    public abstract GameObject createGameObject(String texture);
}
