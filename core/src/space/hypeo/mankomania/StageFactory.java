package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.RouletteStage;
import space.hypeo.mankomania.stages.TitleStage;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.networking.network.NetworkPlayer;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    private StageFactory() {}

    public static Stage getMapStage(final Viewport viewport, final StageManager stageManager){
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

    /**
     * Shows the network-lobby for client and host.
     * @param viewport
     * @param stageManager
     * @return stage/view of lobby
     */
    public static Stage getLobbyStage(final Viewport viewport, final StageManager stageManager, NetworkPlayer player) {
        return new LobbyStage(stageManager, viewport, player);
    }

    /**
     * Shows all discovered hosts for a client.
     * Client can choose a host to connect with.
     * @param viewport
     * @param stageManager
     * @return stage/view of discovered hosts for client
     */
    public static Stage getDiscoveredHostsStage(final Viewport viewport, final StageManager stageManager, NetworkPlayer player) {
        return new DiscoveredHostsStage(stageManager, viewport, player);
    }
    public static Stage getRouletteStage(final Viewport viewport, final StageManager stageManager) {
        return new RouletteStage(viewport,stageManager);
    }

}
