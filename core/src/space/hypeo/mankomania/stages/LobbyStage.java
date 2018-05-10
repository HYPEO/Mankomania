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
import space.hypeo.mankomania.actors.RectangleActor;
import space.hypeo.networking.network.WhatAmI;

import space.hypeo.networking.network.CRole;
import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.packages.Player;


/**
 * Shows the network-lobby:
 * The lobby is a Scene in the game for players to join before playing the actual game.
 * In the lobby, players can pick options and set themselves as ready for the game to start.
 *
 * The list in the lobby creates each player - both host and clients - itself.
 * Therefore the hashmap 'players' - a field of class MHost - contains the necessary data.
 */
public class LobbyStage extends Stage {
    StageManager stageManager;

    public LobbyStage(StageManager stageManager, Viewport viewport) {
        super(viewport);
        this.stageManager = stageManager;

        // Create actors.
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label lblLobby = new Label("GAME LOBBY", skin);

        Table tblLobby = new Table();
        tblLobby.setWidth(this.getWidth());
        tblLobby.align(Align.center);
        tblLobby.setPosition(0, this.getHeight() - 200);
        tblLobby.padTop(50);
        tblLobby.add(lblLobby).width(300).height(100);
        tblLobby.row();

        Lobby lobby = WhatAmI.getLobby();
        CRole role = WhatAmI.getRole();

        if( lobby == null || role.equals(CRole.Role.NOT_CONNECTED) ) {
            Log.error("LobbyStage: lobby must not be null!");
            stageManager.remove(LobbyStage.this);
            return;
        }

        // TODO: game lobby has to be updated after changes in collection 'players'
        int index = 1;
        for( HashMap.Entry<String, Player> entry : lobby.getData().entrySet() ) {

            Button btnPlayer = new TextButton(index + ". " + entry.getKey(), skin);

            btnPlayer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: set status read to start game
                }

            });

            tblLobby.add(btnPlayer).width(300).height(100);
            tblLobby.row();

            index++;
        }

        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add actors.
        this.addActor(background);
        this.addActor(tblLobby);

        // Add listener for click events.
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(LobbyStage.this);
            }
        });
    }
}
