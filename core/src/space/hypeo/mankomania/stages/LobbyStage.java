package space.hypeo.mankomania.stages;

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
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.networking.network.RawPlayer;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.Lobby;
import space.hypeo.networking.network.NetworkPlayer;


/**
 * Shows the network-lobby:
 * The lobby is a Scene in the game for players to join before playing the actual game.
 * In the lobby, players can pick options and set themselves as ready for the game to start.
 */
public class LobbyStage extends Stage {
    private StageManager stageManager;
    private final Viewport viewport;
    private NetworkPlayer networkPlayer;
    private boolean update;

    public LobbyStage(StageManager stageManager, Viewport viewport, NetworkPlayer networkPlayer) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;
        this.networkPlayer = networkPlayer;
        this.update = false;
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
                setupLayout();
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
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer table for all widgets */
        Table rootTable = new Table();
        rootTable.setDebug(true); // turn on all debug lines
        rootTable.setFillParent(true);
        this.addActor(rootTable);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Log.info("current width = " + width);
        Log.info("current height = " + height);

        //width = 500;
        //height = 1000;

        //float widthC = width * 0.7f;
        //float heightC = height * 0.5f;

        //tableContainer.setSize(widthC, heightC);
        //tableContainer.setPosition((width - widthC) / 2.0f, (height - heightC) / 2.0f);
        //tableContainer.fillX();

        /* inner table */

        //table.setWidth( this.getWidth() );
        //table.setPosition(0, this.getHeight() / 2);

        Label title = new Label("GAME LOBBY", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        //table.row().colspan(4).expandX().fillX();
        rootTable.add(title).padTop(50).padBottom(50);
        rootTable.row();
        //tableContainer.setActor(table);

        /* buttons */
        Lobby lobby = networkPlayer.getLobby();
        Role role = networkPlayer.getRole();

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
        for( RawPlayer rawPlayer : lobby.getData() ) {

            Log.info("Build GUI widgets for player " + rawPlayer);

            Button btnIndex = new TextButton("" + index, skin);
            Button btnNick = new TextButton(rawPlayer.getNickname(), skin);
            Button btnAddr = new TextButton(rawPlayer.getAddress(), skin);
            Button btnReady = new TextButton("NO", skin);

            btnIndex.scaleBy(2,2);

            btnReady.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }

            });

            btnTable.add(btnIndex).height(btnHeight).width(60);
            btnTable.add(btnNick).height(btnHeight).width(180);
            btnTable.add(btnAddr).height(btnHeight).width(150);
            btnTable.add(btnReady).height(btnHeight).width(60);
            btnTable.row();

            index++;
        }

        rootTable.add(btnTable);

        this.addActor(rootTable);
    }

}
