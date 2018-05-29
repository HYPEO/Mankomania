package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.fields.EmptyFieldActor;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.fields.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.actors.fields.BuildHotel;
import space.hypeo.mankomania.factories.ActorFactory;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class MapStage extends Stage {
    private static final int NUM_OF_FIELDS = 9;

    private static final float MARGIN_X = 20f;
    private static final float MARGIN_Y = 175;
    private static final float FIELD_DISTANCE = 45f;
    private static final int MAX_PLAYERS = 4;
    private DetailActor detailActor;

    public MapStage(Viewport viewport, StageManager stageManager, GameStateManager gameStateManager) {
        super(viewport);
        if(gameStateManager.registeredPlayerCount()> MAX_PLAYERS)
            throw new IllegalArgumentException("gameStateManager must not contain more than "+ MAX_PLAYERS + " players.");

        setUpBackground();
        detailActor = ActorFactory.getDetailActor();
        this.addActor(detailActor);
        detailActor.positionActor(340);

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

        //Setup players and player details.
        List<PlayerDetailActor> playerDetailActors = generateDetailActors();

        Iterator<PlayerDetailActor> playerDetailIterator = playerDetailActors.iterator();
        for (PlayerActor actor : gameStateManager.getPlayers()) {
            PlayerDetailActor detail = playerDetailIterator.next();
            actor.initializeState(firstField, detail);
            this.addActor(actor);
            this.addActor(detail);
        }
    }

    private List<PlayerDetailActor> generateDetailActors()
    {
        List<PlayerDetailActor> playerDetailActors = new ArrayList<>();

        PlayerDetailActor playerDetailActor = new PlayerDetailActor(new Texture("map_assets/player_1.png"));
        playerDetailActor.positionActor(PlayerDetailActor.ScreenPosition.BOTTOM_LEFT);
        playerDetailActors.add(playerDetailActor);

        PlayerDetailActor secondDetailActor = new PlayerDetailActor(new Texture("map_assets/player_2.png"));
        secondDetailActor.positionActor(PlayerDetailActor.ScreenPosition.BOTTOM_RIGHT);
        playerDetailActors.add(secondDetailActor);

        PlayerDetailActor thirdDetailActor = new PlayerDetailActor(new Texture("map_assets/player_3.png"));
        thirdDetailActor.positionActor(PlayerDetailActor.ScreenPosition.TOP_LEFT);
        playerDetailActors.add(thirdDetailActor);

        PlayerDetailActor fourthDetailActor = new PlayerDetailActor(new Texture("map_assets/player_4.png"));
        fourthDetailActor.positionActor(PlayerDetailActor.ScreenPosition.TOP_RIGHT);
        playerDetailActors.add(fourthDetailActor);

        return playerDetailActors;
    }

    private FieldActor generateField(int fieldIndex, float xDirection, float yDirection, float xMargin, float yMargin) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(9);
        // Create new Field
        FieldActor currentField;
        if (random == 0) {
            currentField = new BuildHotel(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("transparent.png"), randomGenerator.nextInt(10), detailActor, this);
        } else if (random >= 1) {
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
