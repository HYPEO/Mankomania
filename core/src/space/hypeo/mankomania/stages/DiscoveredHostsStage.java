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
import space.hypeo.mankomania.actors.RectangleActor;
import space.hypeo.networking.client.MClient;


public class DiscoveredHostsStage extends Stage {
    StageManager stageManager;

    public DiscoveredHostsStage(StageManager stageManager, Viewport viewport) {
        super(viewport);
        this.stageManager = stageManager;

        // Create actors.
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label lblDiscoveredHosts = new Label("Discovered Hosts", skin);

        // show avaliable hosts as buttons in table
        Table tblDiscoveredHosts = new Table();

        tblDiscoveredHosts.setWidth(this.getWidth());
        tblDiscoveredHosts.align(Align.center);
        tblDiscoveredHosts.setPosition(0, this.getHeight() - 200);
        tblDiscoveredHosts.padTop(50);
        tblDiscoveredHosts.add(lblDiscoveredHosts).width(300).height(100);
        tblDiscoveredHosts.row();

        Log.info("Discovered Network: Host-List contains:");

        tblDiscoveredHosts.add(new Label("Discovered Hosts:", skin)).width(300).height(100);
        tblDiscoveredHosts.row();

        List<InetAddress> foundHosts = MClient.getInstance().discoverHosts();

        if( foundHosts != null && ! foundHosts.isEmpty() ) {

            // TODO: create clickable, scrolable list
            int index = 1;
            for (InetAddress hostAddr : foundHosts) {
                Log.info("  host: " + hostAddr.toString());

                Button btnHost = new TextButton(index + ". Host " + hostAddr.toString(), skin);

                btnHost.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {

                        Log.info("Try to connect to host " + hostAddr.toString() + "...");

                        MClient.getInstance().connectToHost(hostAddr);

                        stageManager.push(StageFactory.getLobbyStage(viewport, stageManager));
                    }

                });

                tblDiscoveredHosts.add(btnHost).width(300).height(100);
                tblDiscoveredHosts.row();

                index++;
            }

        } else {
            Log.info("No hosts found!");

            Button btnNoHost = new TextButton("No Hosts found ", skin);
            tblDiscoveredHosts.add(btnNoHost).width(300).height(100);
            tblDiscoveredHosts.row();
        }

        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add actors.
        this.addActor(background);
        this.addActor(tblDiscoveredHosts);

        // Add listener for click events.
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(DiscoveredHostsStage.this);
            }
        });
    }
}
