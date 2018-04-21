package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import space.hypeo.mankomania.behaviour.EmptyFieldBehaviour;
import space.hypeo.mankomania.behaviour.FieldBehaviour;
import space.hypeo.mankomania.behaviour.PlayerBehaviour;
import space.hypeo.spriteforce.GameLayer;
import space.hypeo.spriteforce.GameLayerManager;
import space.hypeo.spriteforce.GameObject;

/**
 * Factory Class that assembles all GameLayers (Views).
 */
public class LayerFactory {

    private static final int NUM_OF_FIELDS = 9;
    private static final float MARGIN_X = 40f;
    private static final float MARGIN_Y = 80f;
    private static final float FIELD_DISTANCE = 40f;
    private static final float FIELD_SCALE = 30f;
    private static final float PLAYER_SCALE = 20f;

    /**
     * Creates the Game's map layer.
     *
     * @param batch   SpriteBatch for rendering.
     * @param manager GameLayerManager for this layer.
     * @return The generated MapLayer.
     */
    public static GameLayer getMapLayer(SpriteBatch batch, GameLayerManager manager) {
        // Create Map Layer
        GameLayer mapLayer = new GdxGameLayer(manager, batch);

        // Create the First Field on the Map.
        FieldBehaviour firstField = new EmptyFieldBehaviour(null, null);
        mapLayer.createGameObject("wall.jpg").addBehaviour(firstField);

        firstField.setFieldPosition(new Vector3(MARGIN_X, MARGIN_Y, 0));
        firstField.setFieldScale(new Vector3(FIELD_SCALE, FIELD_SCALE, 0));

        // Remember the first field as the previous one.
        FieldBehaviour previousField = firstField;

        //Generate and link fields.
        previousField = generateFields(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField, mapLayer);
        previousField = generateFields(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField, mapLayer);
        previousField = generateFields(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapLayer);
        previousField = generateFields(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapLayer);

        // Link the last field with the first one.
        previousField.setNextField(firstField);


        // Create player on first field.
        PlayerBehaviour player = new PlayerBehaviour("1", 1000, true, firstField);
        GameObject playerGameObject = mapLayer.createGameObject("tile.png");
        playerGameObject.addBehaviour(player);
        playerGameObject.setScale(new Vector3(PLAYER_SCALE, PLAYER_SCALE, 0));

        return mapLayer;
    }

    /**
     * Generates fields and links them.
     * @param amount Amount of fields that should be generated using this pattern.
     * @param xDirection Field spacing w.r.t x-Axis.
     * @param yDirection Field spacing w.r.t y-Axis.
     * @param xMargin Where field creation should begin w.r.t. x-Axis.
     * @param yMargin Where field creation should begin w.r.t. y-Axis.
     * @param firstField The row of fields will be linked to this one.
     * @param mapLayer The GameLayer to create the map on.
     * @return The last field on this list of fields (to use for linking).
     */
    private static FieldBehaviour generateFields(int amount,
                                                float xDirection,
                                                float yDirection,
                                                float xMargin,
                                                float yMargin,
                                                FieldBehaviour firstField,
                                                GameLayer mapLayer) {
        FieldBehaviour previousField = firstField;
        for (int i = 1; i <= amount; i++) {
            // Create new Field and link its behaviour.
            FieldBehaviour currentField = new EmptyFieldBehaviour(null, null);
            mapLayer.createGameObject("wall.jpg").addBehaviour(currentField);
            currentField.setFieldPosition(new Vector3(xMargin + (i * xDirection), yMargin + (i * yDirection), 0));
            currentField.setFieldScale(new Vector3(FIELD_SCALE, FIELD_SCALE, 0));

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }
}
