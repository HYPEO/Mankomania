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

import java.util.HashMap;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.WhatAmI;

import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.network.Player;


/**
 * Shows the network-lobby:
 * The lobby is a Scene in the game for players to join before playing the actual game.
 * In the lobby, players can pick options and set themselves as ready for the game to start.
 */
public class LobbyStage extends Stage {
    private StageManager stageManager;
    private final Viewport viewport;

    private Skin skin;
    private RectangleActor background;
    private Table layout;
    private Label title;

    private Lobby lobby;

    public LobbyStage(StageManager stageManager, Viewport viewport) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;

        updateLobby();
    }

    public void updateLobby() {

        setupBackground();
        setupLayout();
        setupLobby();

        this.addActor(background);
        this.addActor(layout);
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

        title = new Label("GAME LOBBY", skin);

        layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.center);
        layout.setPosition(0, this.getHeight() - 200);
        layout.padTop(50);
        layout.add(title).width(300).height(100);
        layout.row();
    }

    private void setupLobby() {

        lobby = WhatAmI.getLobby();
        Role role = WhatAmI.getRole();

        if( lobby == null || role == Role.NOT_CONNECTED ) {
            Log.error("LobbyStage: lobby must not be null!");
            stageManager.remove(LobbyStage.this);
            return;
        }

        int index = 1;
        for( HashMap.Entry<String, Player> entry : lobby.getData().entrySet() ) {

            Button btnPlayer = new TextButton(
                    index + ": " + entry.getKey() + ", " + entry.getValue().getAddress(),
                    skin);

            btnPlayer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: set status read to start game
                }

            });

            layout.add(btnPlayer).width(300).height(100);
            layout.row();

            index++;
        }


    }
}
