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
 * Created by pichlermarc on 09.05.2018.
 */
public class MainMenuStage extends Stage {
    StageManager stageManager;
    private final Button launch;
    private final Button host;
    private final Button join;

    public MainMenuStage(StageManager stageManager, Viewport viewport)
    {
        super(viewport);
        
        this.stageManager = stageManager;

        // Set up skin
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up buttons.
        launch = new TextButton("Launch Game (offline)", skin);
        host = new TextButton("Host Game", skin);
        join = new TextButton("Join Game", skin);
        Button testSendMoney = new TextButton("Test Send money",skin); // TODO: Temporary button, remove this.


        Label title = new Label("MANKOMANIA (insert logo here)", skin);

        // Add click listeners.
        launch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(StageFactory.getMapStage(viewport, stageManager));
            }
        });

        testSendMoney.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(StageFactory.getSendMoneyStage(viewport, stageManager));
            }
        });

        host.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start server...");

                MHost mHost = new MHost();
                mHost.startServer();

                Log.info("Server has started successfully");
            }

        });

        join.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Log.info("Try to start client...");

                MClient mClient = new MClient();
                mClient.startClient();

                Log.info("Client has started successfully");

                List<InetAddress> foundHosts = mClient.discoverHosts();

                if( foundHosts == null || foundHosts.isEmpty() ) {
                    Log.info("No hosts found!");
                    return;
                }

                Log.info("Discovered Network: Host-List contains:");

                for( InetAddress iAddr : foundHosts ) {
                    Log.info("  host: " + iAddr.toString());
                }

                InetAddress firstHost = foundHosts.get(0);

                Log.info("Try to connect to first host " + firstHost.toString() + "...");

                mClient.connectToHost(firstHost);


            }
        });

        Table table = new Table();
        table.setWidth(this.getWidth());
        table.align(Align.center);
        table.setPosition(0, this.getHeight() - 200);
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
        this.addActor(table);

    }
}
