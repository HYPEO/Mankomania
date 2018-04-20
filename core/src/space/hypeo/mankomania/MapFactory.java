package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import space.hypeo.mankomania.behaviour.EmptyFieldBehaviour;
import space.hypeo.mankomania.behaviour.FieldBehaviour;
import space.hypeo.spriteforce.GameLayer;
import space.hypeo.spriteforce.GameLayerManager;
import space.hypeo.spriteforce.GameObject;

public class MapFactory {

    private static final int NUM_OF_FIELDS = 9;

    /**
     * Creates the Game's map layer.
     * @param batch SpriteBatch for rendering.
     * @param manager GameLayerManager for this layer.
     * @return The generated MapLayer.
     */
    public static GameLayer getMapLayer(SpriteBatch batch, GameLayerManager manager)
    {
        // Create Map Layer
        GameLayer mapLayer = new GdxGameLayer(manager, batch);

        // Create the First Field on the Map.
        FieldBehaviour firstField = new EmptyFieldBehaviour(null, null);
        mapLayer.createGameObject("wall.jpg").addBehaviour(firstField);

        // Remember the first field as the previous one.
        FieldBehaviour previousField = firstField;

        // Generate all fields and link them.
        for (int i = 1; i<NUM_OF_FIELDS; i++)
        {
            // Create new Field and link its behaviour.
            FieldBehaviour currentField = new EmptyFieldBehaviour(null, null);
            mapLayer.createGameObject("wall.jpg").addBehaviour(currentField);
            currentField.setFieldPosition(new Vector3(80 * i, 30, 0));

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        // Link the last field with the first one.
        previousField.setNextField(firstField);

        return mapLayer;
    }
}
