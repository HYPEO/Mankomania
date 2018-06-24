package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.factories.ActorFactory;
import space.hypeo.mankomania.factories.ButtonFactory;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.Role;


/**
 * Shows the network-lobby:
 * The lobby is a Scene in the game for players to join before playing the actual game.
 * In the lobby, players can pick options and set themselves as ready for the game to start.
 */
public class LobbyStage extends Stage {
    private StageManager stageManager;
    private final Viewport viewport;
    private final StageFactory stageFactory;
    private PlayerManager playerManager;
    private boolean update;
    private boolean triggerMapStage;

    public LobbyStage(StageManager stageManager, Viewport viewport, StageFactory stageFactory, PlayerManager playerManager) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;
        this.stageFactory = stageFactory;
        this.playerManager = playerManager;
        this.update = false;
        this.triggerMapStage = false;
        setupBackground();
        setupLayout();
    }

    public void updateLobby() {
        synchronized (this) {
            update = true;
        }
    }

    @Override
    public void act(float delta)
    {
        synchronized (this)
        {
            if(update) {
                this.clear();
                setupBackground();
                setupLayout();
                update = false;
            }
            if(triggerMapStage)
            {
                createMapStage();
            }
        }
    }

    private void setupBackground() {
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);
        this.addActor(background);
    }

    private void setupLayout() {


        Lobby lobby = playerManager.getLobby();
        Role role = playerManager.getRole();
        PlayerSkeleton myself = playerManager.getPlayerSkeleton();

        Log.info(role + ": " + "LobbyStage:setupLayout() ...");

        if(lobby == null) {
            Log.error("LobbyStage: lobby must not be null!");
            stageManager.remove(LobbyStage.this);
            return;
        }

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer table for all widgets */
        Table rootTable = new Table();
        //rootTable.setDebug(true); // turn on all debug lines
        rootTable.setFillParent(true);
        this.addActor(rootTable);

        Button btnBack = ButtonFactory.getButton("common/back_to_main_menu.png", "common/back_to_main_menu_clicked.png");
        btnBack.setTransform(true);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(LobbyStage.this);
            }
        });

        Label title = new Label("GAME LOBBY", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        rootTable.add(title).padTop(50).padBottom(50);
        rootTable.row();

        /* subtitle */
        Table subTitleTable = new Table();
        Label subTitle = new Label("I'm a " + role, skin);
        subTitle.setAlignment(Align.center);
        subTitleTable.add(subTitle).padTop(20).padBottom(20);
        subTitleTable.row();

        rootTable.add(subTitleTable);
        rootTable.row();

        /* inner table contains players from lobby: represented as button */
        Table btnTable = new Table();
        int btnHeight = 60;

        /* header row */
        Label hIndex = new Label("#", skin);
        Label hNick = new Label("Nickname", skin);
        Label hAddr = new Label("IP Address", skin);
        Label hReady = new Label("?", skin);

        btnTable.add(hIndex).height(btnHeight).width(60).align(Align.right);
        btnTable.add(hNick).height(btnHeight).width(180);
        btnTable.add(hAddr).height(btnHeight).width(150);
        btnTable.add(hReady).height(btnHeight).width(60).align(Align.right);
        btnTable.row();

        /* data rows */
        int index = 1;
        for(PlayerSkeleton playerSkeleton : lobby.values()) {

            boolean isThisMe = playerSkeleton.equals(myself);

            Button btnIndex = new TextButton("" + index, skin);
            Button btnNick = new TextButton(playerSkeleton.getNickname(), skin);
            Button btnAddr = new TextButton(playerSkeleton.getAddress(), skin);
            Button btnReady = new TextButton( (playerSkeleton.isReady() ? "YES" : "NO"), skin);

            Color color = playerSkeleton.getColor();
            if(color != null) {
                btnNick.setColor(color);
            }

            /* only host can kick clients */
            if(role == Role.HOST && !isThisMe) {

                btnIndex.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Log.info("invoke player manager to kick " + playerSkeleton);
                        playerManager.kickPlayer(playerSkeleton);
                    }
                });
            }

            /* toggle own ready-to-play-status */
            if(isThisMe) {
                btnReady.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playerManager.toggleReadyStatus();
                    }
                });
            }

            /* change own color */
            if(isThisMe) {
                btnNick.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stageManager.push(stageFactory.getSetColorStage(playerManager));
                    }
                });
            }

            btnTable.add(btnIndex).height(btnHeight).width(60);
            btnTable.add(btnNick).height(btnHeight).width(180);
            btnTable.add(btnAddr).height(btnHeight).width(150);
            btnTable.add(btnReady).height(btnHeight).width(60);
            btnTable.row();

            index++;
        }

        /* add buttons */
        rootTable.add(btnTable);
        rootTable.row();

        /* only host can start the game */
        if(role == Role.HOST &&
                lobby.areAllPlayerReady() &&
                lobby.areAllPlayerColored()) {

            Table startTable = new Table();

            Button btnStartGame = new TextButton("START GAME", skin);
            btnStartGame.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playerManager.createPlayerActor();
                }
            });

            startTable.add(btnStartGame).height(btnHeight).width(250);
            startTable.row();

            rootTable.add(startTable);
        }
        rootTable.row();
        rootTable.add(btnBack).width(400).height(125);
        rootTable.row();
        this.addActor(rootTable);
    }

    private void createMapStage(){
        if (!playerManager.getLobby().areAllPlayerReady()) {
            Log.info("Not all player are ready to start game!");
            return;
        }

        ActorFactory actorFactory = new ActorFactory(stageManager);

        for (PlayerSkeleton ps : playerManager.getLobby().values()) {
            /* add all player except myself */
            Log.info("create from PlayerSkeleton " + ps + " a PlayerActor");

            actorFactory.getPlayerActor(
                    ps.getPlayerID(), ps.getNickname(), ps.getColor(),
                    ps.equals(playerManager.getPlayerSkeleton()),
                    playerManager, stageFactory);
        }

        playerManager.startGame();
        stageManager.push(stageFactory.getMapStage(playerManager));
    }

    public void triggerMapStage(){
        synchronized (this) {
            triggerMapStage = true;
        }
    }
}
