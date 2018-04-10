package space.hypeo.spriteforce;

import java.util.Stack;

import space.hypeo.spriteforce.GameLayer;

/**
 * Created by pichlermarc on 06.04.2018.
 */

/**
 * Holds multiple GameLayers as a Stack, and provides functions useful for switching between layers.
 */
public class GameLayerManager {
    protected Stack<GameLayer> layers;

    /**
     * Creates a new instance of the GameLayerManager class.
     */
    public GameLayerManager()
    {
        layers = new Stack<GameLayer>();
    }

    /**
     * Updates the currently selected layer.
     * @param deltaTime
     */
    public void update(float deltaTime)
    {
        layers.peek().update(deltaTime);
    }

    /**
     * Pushes a layer onto the layer stack and starts rendering this layer.
     * @param layer
     */
    public void push(GameLayer layer)
    {
        layers.push(layer);
    }

    /**
     * Removes the top layer and renders the layer underneath.
     */
    public void pop()
    {
        if(!layers.empty())
            layers.pop();
    }

    /**
     * Removes focus if the specified layer is the current one, and renders the layer underneath.
     * @param layer
     */
    public void defocus(GameLayer layer)
    {
        if(layers.peek() == layer)
        {
            pop();
        }
    }
}
