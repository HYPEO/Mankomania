package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import space.hypeo.mankomania.actors.player.LocalPlayerActor;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.TitleStage;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.SendMoneyStage;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    private final Viewport viewport;
    private final StageManager stageManager;
    private IDeviceStatePublisher publisher;

    public StageFactory(final Viewport viewport, final StageManager stageManager, final IDeviceStatePublisher publisher) {
        this.viewport = viewport;
        this.stageManager = stageManager;
        this.publisher = publisher;
    }

    public Stage getMapStage()
    {
        List<PlayerActor> playerActors = new ArrayList<>();
        playerActors.add(new LocalPlayerActor(new Image(new Texture("players/player_1.png")),
                1000000,
                stageManager,
                this));
        return new MapStage(viewport, stageManager, playerActors);
    }

    public Stage getDiceResultStage(int moveFields) {
        return new DiceResultStage(viewport, stageManager, moveFields);
    }

    public Stage getMainMenu() {
            return new MainMenuStage(stageManager, viewport, this, publisher);
    }

    public Stage getSendMoneyStage() {
        return new SendMoneyStage(viewport, stageManager);
    }


    public Stage getTitleStage() {
            return new TitleStage(stageManager, viewport);
    }

    /**
     * Shows the network-lobby for client and host.
     * @return stage/view of lobby
     */
    public Stage getLobbyStage(PlayerManager playerManager) {
        return new LobbyStage(stageManager, viewport, playerManager);
    }

    /**
     * Shows all discovered hosts for a client.
     * Client can choose a host to connect with.
     * @return stage/view of discovered hosts for client
     */
    public Stage getDiscoveredHostsStage(PlayerManager playerManager) {
        return new DiscoveredHostsStage(stageManager, viewport, this, playerManager);
    }

}
