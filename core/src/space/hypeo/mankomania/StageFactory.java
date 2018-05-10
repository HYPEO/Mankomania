package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;
import space.hypeo.mankomania.actors.BuyHouseFieldActor;
import space.hypeo.mankomania.actors.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.EmptyFieldActor;
import space.hypeo.mankomania.actors.FieldActor;
import space.hypeo.mankomania.actors.PlayerActor;
import space.hypeo.mankomania.stages.DiscoveredHostsStage;
import space.hypeo.mankomania.stages.LobbyStage;
import space.hypeo.mankomania.stages.TitleStage;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.WhatAmI;
import space.hypeo.mankomania.stages.DiceResultStage;
import space.hypeo.mankomania.stages.MainMenuStage;
import space.hypeo.mankomania.stages.MapStage;
import space.hypeo.mankomania.stages.SendMoneyStage;
import space.hypeo.mankomania.stages.TitleStage;

/**
 * Creates all the stages (views) for the game.
 */
public class StageFactory {
    public static Stage getMainMenuLegacy(final Viewport viewport, final StageManager stageManager) {
        Stage mainMenuStage = new Stage(viewport);


        // Set up skin
        Skin skin = new Skin(Gdx.files.internal(PATH_UISKIN));

        // Set up buttons.
        Button launch = new TextButton("Launch Game (offline)", skin);
        Button host = new TextButton("Host Game", skin);
        Button join = new TextButton("Join Game", skin);
        Button testSendMoney = new TextButton("Test Send money",skin);

        Label title = new Label("MANKOMANIA (insert logo here)", skin);

        // Add click listeners.
        launch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(getMapStage(viewport, stageManager));
            }
        });

        testSendMoney.addListener(new ClickListener() {
                                      public void clicked(InputEvent event, float x, float y) {
                                          stageManager.push(StageFactory.getSendMoneyDialog(viewport, stageManager));
                                      }
                                  });

        host.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start server...");
                // initialize device as host
                WhatAmI.init("the_mighty_host", Role.HOST);
                // start server process
                WhatAmI.getHost().startServer();
                Log.info("Server has started successfully");

                stageManager.push(StageFactory.getLobbyStage(viewport, stageManager));
            }

        });

        join.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start client...");
                // initialize device as client
                WhatAmI.init("another_client", Role.CLIENT);
                // start client process
                WhatAmI.getClient().startClient();
                Log.info("Client has started successfully");

                stageManager.push(StageFactory.getDiscoveredHostsStage(viewport, stageManager));
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
        table.row();
        table.add(testSendMoney).width(300).height(100);
        table.row();

        // Add buttons to stage.
        mainMenuStage.addActor(table);

        return mainMenuStage;
    }

    public static Stage getMapStage(final Viewport viewport, final Stagemanager stagemanager){
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
     * Shows the network-lobby.
     * @param viewport
     * @param stageManager
     * @return stage/view of lobby
     */
    public static Stage getLobbyStage(final Viewport viewport, final StageManager stageManager)
    {
        return new LobbyStage(stageManager, viewport);
    }

    /**
     * Discovers hosts in WLAN after hit "Join Game" button.
     * Shows all discovered hosts. Hosts can be chosen by button-click.
     * That Stage can only be visited as role "cient".
     * @param viewport
     * @param stageManager
     * @return stage/view of discovered hosts for client
     */
    public static Stage getDiscoveredHostsStage(final Viewport viewport, final StageManager stageManager)
    {
        return new DiscoveredHostsStage(stageManager, viewport);
    }

}
