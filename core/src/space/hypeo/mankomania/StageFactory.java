package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.TitleStage;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.networking.network.IDeviceStatePublisher;
import space.hypeo.networking.network.NetworkPlayer;

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
        playerActors.add(new PlayerActor("1", 1000000, true, stageManager, this));
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
    public Stage getLobbyStage(NetworkPlayer player) {
        return new LobbyStage(stageManager, viewport, player);
    }

    /**
     * Shows all discovered hosts for a client.
     * Client can choose a host to connect with.
     * @return stage/view of discovered hosts for client
     */
    public Stage getDiscoveredHostsStage(NetworkPlayer player) {
        return new DiscoveredHostsStage(stageManager, viewport, player, this);
    }

}
