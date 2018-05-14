package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.fields.BuyHotelFieldActor;
import space.hypeo.mankomania.actors.fields.EmptyFieldActor;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.fields.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.factories.ActorFactory;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class MapStage extends Stage {
    private static final int NUM_OF_FIELDS = 9;
    private static final float MARGIN_X = 20f;
    private static final float MARGIN_Y = 175;
    private static final float FIELD_DISTANCE = 45f;
    private DetailActor detailActor;

    public MapStage(Viewport viewport, StageManager stageManager) {
        super(viewport);
        setUpBackground();
        detailActor = ActorFactory.getDetailActor();
        this.addActor(detailActor);
        detailActor.positionActor(340);

        PlayerDetailActor playerDetailActor = new PlayerDetailActor(new Texture("map_assets/player_1.png"));
        playerDetailActor.positionActor(PlayerDetailActor.ScreenPosition.BOTTOM_LEFT);
        this.addActor(playerDetailActor);


        PlayerDetailActor secondDetailActor = new PlayerDetailActor(new Texture("map_assets/player_2.png"));
        secondDetailActor.positionActor(PlayerDetailActor.ScreenPosition.BOTTOM_RIGHT);
        this.addActor(secondDetailActor);

        PlayerDetailActor thirdDetailActor = new PlayerDetailActor(new Texture("map_assets/player_3.png"));
        thirdDetailActor.positionActor(PlayerDetailActor.ScreenPosition.TOP_LEFT);
        this.addActor(thirdDetailActor);

        PlayerDetailActor fourthDetailActor = new PlayerDetailActor(new Texture("map_assets/player_4.png"));
        fourthDetailActor.positionActor(PlayerDetailActor.ScreenPosition.TOP_RIGHT);
        this.addActor(fourthDetailActor);


        // Create the empty field.
        FieldActor firstField = new EmptyFieldActor(MARGIN_X, MARGIN_Y, new Texture("transparent.png"), 0, detailActor);
        this.addActor(firstField);

        // Remember the first field as the previous one.
        FieldActor previousField = firstField;


        previousField = generateFieldLine(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField);
        previousField = generateFieldLine(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField);
        previousField = generateFieldLine(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField);
        previousField = generateFieldLine(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField);

        // Link the last field with the first one to create a full loop.
        previousField.setNextField(firstField);

        // Create player on first field.
        PlayerActor player = new PlayerActor("1", 1000000, true, firstField, viewport, stageManager, playerDetailActor);
        this.addActor(player);


    }

    private FieldActor generateField(int fieldIndex, float xDirection, float yDirection, float xMargin, float yMargin) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(9);
        // Create new Field
        FieldActor currentField;
        if (random == 0) {
            currentField = new BuyHotelFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("RumsBuDee.png"), randomGenerator.nextInt(5) + 10, detailActor);
        } else if (random == 1) {
            currentField = new LoseMoneyFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("6dice.png"), randomGenerator.nextInt(10), detailActor);
        } else {
            currentField = new EmptyFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("transparent.png"), 0, detailActor);
        }
        return currentField;
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
     * @return The last field on this list of fields (to use for linking).
     */
    private FieldActor generateFieldLine(int amount,
                                         float xDirection,
                                         float yDirection,
                                         float xMargin,
                                         float yMargin,
                                         FieldActor firstField) {
        FieldActor previousField = firstField;
        for (int i = 1; i <= amount; i++) {
            FieldActor currentField = generateField(i, xDirection, yDirection, xMargin, yMargin);
            this.addActor(currentField);

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }

    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }
}
