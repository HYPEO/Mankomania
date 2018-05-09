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

import java.net.InetAddress;
import java.util.List;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.networking.client.MClient;
import space.hypeo.networking.host.MHost;

/**
 * Holds all widgets on the main menu.
 */
public class MainMenuStage extends Stage {
    private StageManager stageManager;
    private Button launch;
    private Button host;
    private Button join;
    private Label title;
    private Table layout;
    private final Viewport viewport;

    public MainMenuStage(StageManager stageManager, Viewport viewport) {
        super(viewport);

        this.stageManager = stageManager;
        this.viewport = viewport;

        setupClickListeners();
        setupLayout();

        this.addActor(layout);
    }

    private void createWidgets()
    {

        // Set up skin
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up Title.
        title = new Label("MANKOMANIA (insert logo here)", skin);

        // Set up buttons.
        launch = new TextButton("Launch Game (offline)", skin);
        host = new TextButton("Host Game", skin);
        join = new TextButton("Join Game", skin);
    }

    private ClickListener launchClickListener()
    {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(StageFactory.getMapStage(viewport, stageManager));
            }
        };
    }

    private ClickListener hostClickListener()
    {
        return new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start server...");

                MHost mHost = new MHost();
                mHost.startServer();

                Log.info("Server has started successfully");
            }
        };
    }

    private ClickListener clientClickListener()
    {
        return new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start client...");

                MClient mClient = new MClient();
                mClient.startClient();

                Log.info("Client has started successfully");

                List<InetAddress> foundHosts = mClient.discoverHosts();

                if (foundHosts == null || foundHosts.isEmpty()) {
                    Log.info("No hosts found!");
                    return;
                }

                Log.info("Discovered Network: Host-List contains:");

                for (InetAddress iAddr : foundHosts) {
                    Log.info("  host: " + iAddr.toString());
                }

                InetAddress firstHost = foundHosts.get(0);

                Log.info("Try to connect to first host " + firstHost.toString() + "...");
                mClient.connectToHost(firstHost);
            }
        };
    }

    private void setupClickListeners()
    {
        launch.addListener(this.launchClickListener());
        host.addListener(this.hostClickListener());
        join.addListener(clientClickListener());
    }

    private void setupLayout() {
        layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.center);
        layout.setPosition(0, this.getHeight() - 200);
        layout.padTop(50);
        layout.add(title).width(300).height(100);
        layout.row();
        layout.add(launch).width(300).height(100);
        layout.row();
        layout.add(host).width(300).height(100);
        layout.row();
        layout.add(join).width(300).height(100);
        layout.row();
    }
}
