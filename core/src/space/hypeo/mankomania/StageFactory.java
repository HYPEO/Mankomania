package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.actors.horse.HorseActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.factories.ActorFactory;
import space.hypeo.mankomania.factories.FieldFactory;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.EndGameStage;
import space.hypeo.mankomania.stages.HorseRaceResultStage;
import space.hypeo.mankomania.stages.HorseRaceStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.RouletteStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.mankomania.stages.SetColorStage;
import space.hypeo.mankomania.stages.TitleStage;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    private final Viewport viewport;
    private final StageManager stageManager;
    private IDeviceStatePublisher publisher;
    private ActorFactory actorFactory;

    public StageFactory(final Viewport viewport, final StageManager stageManager, final IDeviceStatePublisher publisher, ActorFactory actorFactory) {
        this.viewport = viewport;
        this.stageManager = stageManager;
        this.publisher = publisher;
        this.actorFactory = actorFactory;
    }

    public Stage getMapStage()
    {
        GameStateManager gameStateManager = new OfflineGameStateManager(stageManager, this);

        actorFactory.getPlayerActor("", "", Color.GREEN,true, gameStateManager, this);
        actorFactory.getPlayerActor("", "", Color.CYAN,true, gameStateManager, this);
        actorFactory.getPlayerActor("", "", Color.YELLOW,true, gameStateManager, this);
        actorFactory.getPlayerActor("", "", Color.PINK,true, gameStateManager, this);

        DetailActor detailActor = actorFactory.getDetailActor();
        FieldFactory fieldFactory = new FieldFactory(detailActor);
        return new MapStage(viewport, gameStateManager, detailActor, fieldFactory);
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

    public Stage getHorseRaceStage(PlayerActor playerActor) {
        return new HorseRaceStage(viewport, stageManager, this, playerActor);
    }

    public Stage getHorseRaceResultStage(int backedHorseID, int bet, HorseActor winningHorse) {
        return new HorseRaceResultStage(viewport, stageManager, backedHorseID, bet, winningHorse);
    }

    /**
     * Shows the network-lobby for client and host.
     * @return stage/view of lobby
     */
    public Stage getLobbyStage(PlayerManager playerManager) {
        return new LobbyStage(stageManager, viewport, this, playerManager);
    }

    /**
     * Shows all discovered hosts for a client.
     * Client can choose a host to connect with.
     * @return stage/view of discovered hosts for client
     */
    public Stage getDiscoveredHostsStage(PlayerManager playerManager) {
        return new DiscoveredHostsStage(stageManager, viewport, this, playerManager);
    }
    public static Stage getRouletteStage(final Viewport viewport, final StageManager stageManager) {
        return new RouletteStage(viewport,stageManager);
    }

    public Stage getEndGameStage(PlayerActor winningPlayer) {
        return new EndGameStage(viewport, stageManager, winningPlayer.getPlayerDetailActor());
    }

    public Stage getSetColorStage(PlayerManager playerManager) {
        return new SetColorStage(stageManager, viewport, this, playerManager);
    }
}
