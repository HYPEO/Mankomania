package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.mankomania.stages.TitleStage;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    /**
     * Generates a map stage (view).
     *
     * @param viewport The viewport the stage will be rendered in.
     * @return A stage with the map as its content.
     */
    public static Stage getMapStage(final Viewport viewport, final StageManager stageManager) {
        return new MapStage(viewport, stageManager);
    }


    public static Stage getDiceResultStage(final Viewport viewport, final StageManager stageManager, int moveFields) {
        return new DiceResultStage(viewport, stageManager, moveFields);
    }

    public static Stage getMainMenu(final Viewport viewport, final StageManager stageManager) {
        return new MainMenuStage(stageManager, viewport);
    }

    public static Stage getSendMoneyStage(final Viewport viewport, final StageManager stageManager) {
        return new SendMoneyStage(viewport, stageManager);
    }

    public static Stage getTitleStage(final Viewport viewport, final StageManager stageManager) {
        return new TitleStage(stageManager, viewport);
    }
}
