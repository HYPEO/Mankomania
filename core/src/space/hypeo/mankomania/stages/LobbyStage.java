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
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.Role;
import space.hypeo.mankomania.player.Lobby;


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

    public LobbyStage(StageManager stageManager, Viewport viewport, StageFactory stageFactory, PlayerManager playerManager) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;
        this.stageFactory = stageFactory;
        this.playerManager = playerManager;
        this.update = false;
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
        }
    }

    private void setupBackground() {
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add listener for click on background events.
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(LobbyStage.this);
            }
        });

        this.addActor(background);
    }

    private void setupLayout() {
        Log.info(playerManager.getRole() + ": " + "Build LobbyStage ...");

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer table for all widgets */
        Table rootTable = new Table();
        //rootTable.setDebug(true); // turn on all debug lines
        rootTable.setFillParent(true);
        this.addActor(rootTable);

        Label title = new Label("GAME LOBBY", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        rootTable.add(title).padTop(50).padBottom(50);
        rootTable.row();

        /* buttons */
        Lobby lobby = playerManager.getLobby();
        Role role = playerManager.getRole();

        if( lobby == null || role == Role.NOT_CONNECTED ) {
            Log.error("LobbyStage: lobby must not be null!");
            stageManager.remove(LobbyStage.this);
            return;
        }

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
        for( PlayerSkeleton playerSkeleton : lobby.getData() ) {

            PlayerSkeleton myself = playerManager.getPlayerBusiness().getPlayerSkeleton();

            Button btnIndex = new TextButton("" + index, skin);
            Button btnNick = new TextButton(playerSkeleton.getNickname(), skin);
            Button btnAddr = new TextButton(playerSkeleton.getAddress(), skin);
            Button btnReady = new TextButton( (playerManager.getLobby().getReadyStatus(playerSkeleton) ? "YES" : "NO"), skin);

            Color color = playerSkeleton.getColor();
            if(color != null) {
                btnNick.setColor(color);
            }

            btnIndex.scaleBy(2,2);

            btnIndex.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(playerManager.getRole() == Role.HOST && !playerSkeleton.equals(myself)) {
                        playerManager.kickPlayer(playerSkeleton);
                    }
                }

            });

            btnReady.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if( playerSkeleton.equals(myself)) {
                        playerManager.toggleReadyStatus();
                    }
                }

            });

            btnNick.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(playerSkeleton.equals(myself)) {
                        stageManager.push(stageFactory.getSetColorStage(playerManager));
                    }
                }

            });

            btnTable.add(btnIndex).height(btnHeight).width(60);
            btnTable.add(btnNick).height(btnHeight).width(180);
            btnTable.add(btnAddr).height(btnHeight).width(150);
            btnTable.add(btnReady).height(btnHeight).width(60);
            btnTable.row();

            index++;
        }

        /* add buttons */
        rootTable.add(btnTable);

        this.addActor(rootTable);
    }

}
