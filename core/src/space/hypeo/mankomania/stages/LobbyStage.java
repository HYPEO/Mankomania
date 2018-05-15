package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
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

    private Skin skin;
    private RectangleActor background;
    private Container<Table> tableContainer;

    private boolean updateLobby;
    private float timeSinceLastUpdate;

    public LobbyStage(StageManager stageManager, Viewport viewport, NetworkPlayer player) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;

        this.networkPlayer = player;

        this.updateLobby = false;
        this.timeSinceLastUpdate = 0f;

        updateLobby();
    }

    public void updateLobby() {
        synchronized (this){
            updateLobby = true;
        }
    }

    private void setupBackground() {
        background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add listener for click on background events.
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(LobbyStage.this);
            }
        });
    }

    private void setupLayout() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer container for all widgets */
        tableContainer = new Container<>();

//        float sw = Gdx.graphics.getWidth();
//        float sh = Gdx.graphics.getHeight();

        Log.info("Gdx.graphics.getWidth() = " + Gdx.graphics.getWidth());
        Log.info("Gdx.graphics.getHeight() = " + Gdx.graphics.getHeight());

        float width = 500;
        float height = 1000;

        float widthC = width * 0.7f;
        float heightC = height * 0.5f;

        tableContainer.setSize(widthC, heightC);
        tableContainer.setPosition((width - widthC) / 2.0f, (height - heightC) / 2.0f);
        tableContainer.fillX();

        /* outer table */
        Table table = new Table(skin);
        //table.setWidth( this.getWidth() );
        //table.setPosition(0, this.getHeight() / 2);

        Label title = new Label("GAME LOBBY", skin);
        //title.setFontScaleX(2);
        //title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        table.row().colspan(4).expandX().fillX();
        table.add(title).fillX();
        tableContainer.setActor(table);

        /* buttons */
        Lobby lobby = networkPlayer.registeredPlayers();
        Role role = networkPlayer.getRole();

        if( lobby == null || role == Role.NOT_CONNECTED ) {
            Log.error("LobbyStage: lobby must not be null!");
            stageManager.remove(LobbyStage.this);
            return;
        }

        Table buttonTable = new Table(skin);

        int index = 1;
        for( RawPlayer rawPlayer : lobby.getData() ) {

            Log.info("Build GUI widgets for player " + rawPlayer);

            Button btnIndex = new TextButton("" + index, skin);
            Button btnNick = new TextButton(rawPlayer.getNickname(), skin);
            Button btnAddr = new TextButton(rawPlayer.getAddress(), skin);
            Button btnReady = new TextButton("OK", skin);

            /*btnNick.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: set status read to start game
                }

            });*/

            buttonTable.row().colspan(4).expandX().fillX();
            buttonTable.add(btnIndex).expandX().fillX();
            buttonTable.add(btnNick).expandX().fillX();
            buttonTable.add(btnAddr).expandX().fillX();
            buttonTable.add(btnReady).expandX().fillX();

            index++;
        }

        tableContainer.setActor(buttonTable);
    }

    @Override
    public void act(float delta)
    {
        timeSinceLastUpdate += delta;

        if(timeSinceLastUpdate > 1f) {
            synchronized (this) {
                if (updateLobby) {
                    setupBackground();
                    setupLayout();

                    this.addActor(background);
                    this.addActor(tableContainer);
                }
                updateLobby = false;
            }
            timeSinceLastUpdate = 0f;
        }

        super.act(delta);
    }
}
