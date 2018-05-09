package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.BuyHotelFieldActor;
import space.hypeo.mankomania.actors.fields.EmptyFieldActor;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.fields.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class MapStage extends Stage {
    private static final int NUM_OF_FIELDS = 9;
    private static final float MARGIN_X = 40f;
    private static final float MARGIN_Y = 80f;
    private static final float FIELD_DISTANCE = 40f;
    private static Texture texture = new Texture("badlogic.jpg");
    private static Image fieldInfoImage;

    public MapStage(Viewport viewport, StageManager stageManager) {
        super(viewport);

        fieldInfoImage = new Image(texture);
        fieldInfoImage.setBounds(90, 125, 300, 300);

        // Create the empty field.
        FieldActor firstField = new EmptyFieldActor(MARGIN_X, MARGIN_Y, new Texture("transparent.png"), 0, fieldInfoImage);
        this.addActor(firstField);

        // Remember the first field as the previous one.
        FieldActor previousField = firstField;

        // Generate all sides of the "field rectangle"
        // TODO: This needs some cleanup.

        previousField = generateFields(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField, this);
        previousField = generateFields(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField, this);
        previousField = generateFields(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, this);
        previousField = generateFields(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, this);

        // Link the last field with the first one to create a full loop.
        previousField.setNextField(firstField);

        // Create player on first field.
        PlayerActor player = new PlayerActor("1", 1000000, true, firstField, viewport, stageManager);

        this.addActor(player);

        // Create close button.
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Button close = new TextButton("Close Game", skin);
        close.setHeight(100);
        close.setWidth(viewport.getWorldWidth());
        close.setY(viewport.getWorldHeight() - close.getHeight());
        // Add click listeners.
        close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(MapStage.this);
            }
        });
        this.addActor(close);
        this.addActor(fieldInfoImage);

    }

    /**
     * Generates fields and links them.
     *
     * @param amount     Amount of fields that should be generated using this pattern.
     * @param xDirection Field spacing w.r.t x-Axis.
     * @param yDirection Field spacing w.r.t y-Axis.
     * @param xMargin    Where field creation should begin w.r.t. x-Axis.
     * @param yMargin    Where field creation should begin w.r.t. y-Axis.
     * @param firstField The row of fields will be linked to this one.
     * @param mapStage   The stage to create the map on.
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
            int random = (int) (Math.random() * 9);

            // Create new Field
            FieldActor currentField;
            if (random == 0) {
                currentField = new BuyHotelFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection), new Texture("RumsBuDee.png"), (int) (Math.random() * 50 + 10), fieldInfoImage);
            } else if (random == 1) {
                currentField = new LoseMoneyFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection), new Texture("6dice.png"), (int) (Math.random() * 10), fieldInfoImage);
            } else {
                currentField = new EmptyFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection), new Texture("transparent.png"), 0, fieldInfoImage);
            }

            mapStage.addActor(currentField);

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }
}
