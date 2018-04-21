package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.hypeo.mankomania.actors.EmptyFieldActor;
import space.hypeo.mankomania.actors.FieldActor;
import space.hypeo.mankomania.actors.PlayerActor;

public class StageFactory {
    private static final int NUM_OF_FIELDS = 9;
    private static final float MARGIN_X = 40f;
    private static final float MARGIN_Y = 80f;
    private static final float FIELD_DISTANCE = 40f;

    public static Stage getMapStage(Viewport viewport)
    {
        Stage mapStage = new Stage(viewport);
        FieldActor firstField = new EmptyFieldActor(MARGIN_X, MARGIN_Y);
        mapStage.addActor(firstField);
        // Remember the first field as the previous one.
        FieldActor previousField = firstField;

        previousField = generateFields(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapStage);

        previousField.setNextField(firstField);

        // Create player on first field.
        PlayerActor player = new PlayerActor("1", 1000, true, firstField);
        mapStage.addActor(player);

        return mapStage;
    }


    /**
     * Generates fields and links them.
     * @param amount Amount of fields that should be generated using this pattern.
     * @param xDirection Field spacing w.r.t x-Axis.
     * @param yDirection Field spacing w.r.t y-Axis.
     * @param xMargin Where field creation should begin w.r.t. x-Axis.
     * @param yMargin Where field creation should begin w.r.t. y-Axis.
     * @param firstField The row of fields will be linked to this one.
     * @param mapStage The stage to create the map on.
     * @return The last field on this list of fields (to use for linking).
     */
    private static FieldActor generateFields(int amount,
                                        float xDirection,
                                        float yDirection,
                                        float xMargin,
                                        float yMargin,
                                        FieldActor firstField,
                                        Stage mapStage) {
        FieldActor previousField = firstField;
        for (int i = 1; i <= amount; i++) {
            // Create new Field and link its behaviour.
            FieldActor currentField = new EmptyFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection));
            mapStage.addActor(currentField);

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }
}
