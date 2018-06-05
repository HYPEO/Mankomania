package space.hypeo.mankomania.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.fields.EmptyFieldActor;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.factories.FieldFactory;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class MapStage extends Stage {
    private static final int NUM_OF_FIELDS = 9;

    private static final float MARGIN_X = 20f;
    private static final float MARGIN_Y = 175;
    private static final float FIELD_DISTANCE = 45f;
    private static final int MAX_PLAYERS = 4;


    public MapStage(Viewport viewport, GameStateManager gameStateManager, DetailActor detailActor, FieldFactory fieldFactory) {
        super(viewport);
        if(gameStateManager.registeredPlayerCount()> MAX_PLAYERS)
            throw new IllegalArgumentException("gameStateManager must not contain more than "+ MAX_PLAYERS + " players.");

        setUpBackground();
        this.addActor(detailActor);
        detailActor.positionActor(340);

        // Create the empty field.
        FieldActor firstField = new EmptyFieldActor(MARGIN_X, MARGIN_Y, new Texture("transparent.png"), 0, detailActor);
        this.addActor(firstField);

        // Remember the first field as the previous one.
        FieldActor previousField = firstField;

        previousField = fieldFactory.generateFieldLine(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField, this);
        previousField = fieldFactory.generateFieldLine(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField, this);
        previousField = fieldFactory.generateFieldLine(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, this);
        previousField = fieldFactory.generateFieldLine(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, this);

        // Link the last field with the first one to create a full loop.
        previousField.setNextField(firstField);


        for (PlayerActor actor : gameStateManager.getPlayers()) {
            actor.initializeState(firstField);
            this.addActor(actor);
            this.addActor(actor.getPlayerDetailActor());
        }
    }



    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }
}
