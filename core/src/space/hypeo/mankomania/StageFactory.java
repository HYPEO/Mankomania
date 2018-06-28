package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.actors.fields.BuildHotel;
import space.hypeo.mankomania.actors.horse.HorseActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.factories.ActorFactory;
import space.hypeo.mankomania.factories.FieldFactory;
import space.hypeo.mankomania.game.SlotMachineLogic;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.stages.BuildHotelStage;
import space.hypeo.mankomania.stages.ClickerStage;
import space.hypeo.mankomania.stages.ClickerStageEndscreen;
import space.hypeo.mankomania.stages.CreatePlayerActorStage;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.EndGameStage;
import space.hypeo.mankomania.stages.EvilOneStage;
import space.hypeo.mankomania.stages.FinishedHotelStage;
import space.hypeo.mankomania.stages.HorseRaceResultStage;
import space.hypeo.mankomania.stages.HorseRaceStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.LotteryStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.RouletteStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.mankomania.stages.SetColorStage;
import space.hypeo.mankomania.stages.SlotMachineResultStage;
import space.hypeo.mankomania.stages.SlotMachineStage;
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

        actorFactory.getPlayerActor("Player 1", "Player 1", Color.GREEN,true, gameStateManager, this);
        actorFactory.getPlayerActor("Player 2", "Player 2", Color.CYAN,true, gameStateManager, this);
        actorFactory.getPlayerActor("Player 3", "Player 3", Color.YELLOW,true, gameStateManager, this);
        actorFactory.getPlayerActor("Player 4", "Player 4", Color.PINK,true, gameStateManager, this);

        return getMapStage(gameStateManager);
    }

    public Stage getMapStage(GameStateManager gameStateManager)
    {
        DetailActor detailActor = actorFactory.getDetailActor();
        FieldFactory fieldFactory = new FieldFactory(detailActor, stageManager, this);
        return new MapStage(viewport, gameStateManager, detailActor, fieldFactory);
    }

    public Stage getDiceResultStage(int moveFields) {
        return new DiceResultStage(viewport, stageManager, moveFields);
    }

    public Stage getMainMenu() {
        return new MainMenuStage(stageManager, viewport, this, publisher);
    }

    public Stage getSendMoneyStage(PlayerManager playerManager) {
        return new SendMoneyStage(viewport,stageManager,this,playerManager);
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

    public LotteryStage getLotteryStage(PlayerActor playerActor, boolean pay)
     {
        return new LotteryStage(viewport, stageManager, playerActor, pay);
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

    public Stage getEvilOneStage(PlayerActor playerActor){
        return new EvilOneStage(viewport,stageManager,playerActor);
    }
    public Stage getEndGameStage(PlayerActor winningPlayer) {
        return new EndGameStage(viewport, stageManager, winningPlayer.getPlayerDetailActor());
    }

    public Stage getSetColorStage(PlayerManager playerManager) {
        return new SetColorStage(stageManager, viewport, playerManager);
    }

    public Stage getCreatePlayerActorStage(PlayerManager playerManager) {
        return new CreatePlayerActorStage(viewport, playerManager);
    }

    public Stage getSlotMachineStage(PlayerActor playerActor) {
        return new SlotMachineStage(viewport, stageManager, this, playerActor);
    }

    public Stage getSlotMachineResultStage(SlotMachineLogic slotMachineLogic) {
        return new SlotMachineResultStage(viewport, stageManager, slotMachineLogic);
    }

    public Stage buildHotelStage(PlayerActor player, BuildHotel build) {
        return  new BuildHotelStage(viewport,stageManager,player,build);
    }

    public Stage finishedHotelStage(PlayerActor player, boolean owner) {
        return new FinishedHotelStage(viewport,stageManager,player,owner );
    }
    public Stage clickerStage(PlayerActor player) {
        return new ClickerStage(viewport,stageManager,this,player);
    }
    public Stage clickerStageEndscreen(PlayerActor playerActor, int score) {
        return new ClickerStageEndscreen(viewport,stageManager, playerActor,score);
    }

}
