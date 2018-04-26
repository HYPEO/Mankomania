package space.hypeo.mankomania;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.actors.BuyHouseFieldActor;
import space.hypeo.mankomania.actors.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.EmptyFieldActor;
import space.hypeo.mankomania.actors.FieldActor;
import space.hypeo.mankomania.actors.PlayerActor;
import space.hypeo.mankomania.stages.TitleStage;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    private static final int NUM_OF_FIELDS = 9;
    private static final float MARGIN_X = 40f;
    private static final float MARGIN_Y = 80f;
    private static final float FIELD_DISTANCE = 40f;

    /**
     * Generates a map stage (view).
     *
     * @param viewport The viewport the stage will be rendered in.
     * @return A stage with the map as its content.
     */
    public static Stage getMapStage(final Viewport viewport, final StageManager stageManager) {
        // Create stage.
        final Stage mapStage = new Stage(viewport);
        // Create the empty field.
        FieldActor firstField = new EmptyFieldActor(MARGIN_X, MARGIN_Y);
        mapStage.addActor(firstField);

        // Remember the first field as the previous one.
        FieldActor previousField = firstField;

        // Generate all sides of the "field rectangle"
        // TODO: This needs some cleanup.

        previousField = generateFields(NUM_OF_FIELDS, FIELD_DISTANCE, 0, MARGIN_X, MARGIN_Y, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS, 0, FIELD_DISTANCE, MARGIN_X + NUM_OF_FIELDS * FIELD_DISTANCE, MARGIN_Y, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS, -FIELD_DISTANCE, 0, NUM_OF_FIELDS * FIELD_DISTANCE + MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapStage);
        previousField = generateFields(NUM_OF_FIELDS - 1, 0, -FIELD_DISTANCE, MARGIN_X, MARGIN_Y + NUM_OF_FIELDS * FIELD_DISTANCE, previousField, mapStage);

        // Link the last field with the first one to create a full loop.
        previousField.setNextField(firstField);

        // Create player on first field.
        PlayerActor player = new PlayerActor("1", 1000, true, firstField);
        mapStage.addActor(player);

        // Create close button.
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Button close = new TextButton("Close Game", skin);
        close.setHeight(100);
        close.setWidth(viewport.getWorldWidth());
        close.setY(viewport.getWorldHeight()-close.getHeight());
        // Add click listeners.
        close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(mapStage);
            }
        });
        mapStage.addActor(close);

        return mapStage;
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
            int random= (int)(Math.random() * 9);

            // Create new Field
            FieldActor currentField;
            if(random==0)
             currentField = new BuyHouseFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection));
            else if(random==1)
                currentField = new LoseMoneyFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection));
            else
                currentField = new EmptyFieldActor(xMargin + (i * xDirection), yMargin + (i * yDirection));

            mapStage.addActor(currentField);

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }


    public static Stage getMainMenu(final Viewport viewport, final StageManager stageManager) {
        Stage mainMenuStage = new Stage(viewport);

        // Set up skin
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up buttons.
        Button launch = new TextButton("Launch Game (offline)", skin);
        Button host = new TextButton("Host Game", skin);
        Button join = new TextButton("Join Game", skin);

        Label title = new Label("MANKOMANIA (insert logo here)", skin);

        // Add click listeners.
        launch.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(getMapStage(viewport, stageManager));
            }
        });

        Table table = new Table();
        table.setWidth(mainMenuStage.getWidth());
        table.align(Align.center);
        table.setPosition(0, mainMenuStage.getHeight() - 200);
        table.padTop(50);
        table.add(title).width(300).height(100);
        table.row();
        table.add(launch).width(300).height(100);
        table.row();
        table.add(host).width(300).height(100);
        table.row();
        table.add(join).width(300).height(100);

        // Add buttons to stage.
        mainMenuStage.addActor(table);

        return mainMenuStage;
    }

    public static Stage getTitleStage(final Viewport viewport, final StageManager stageManager)
    {
        return new TitleStage(stageManager, viewport);
    }
}
