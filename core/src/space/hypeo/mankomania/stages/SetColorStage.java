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

import java.util.Set;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.player.Colors;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.network.Role;

/**
 * This stage is the view to set the player color.
 * It will be called out of the LobbyStage.
 */
public class SetColorStage extends Stage {
    private StageManager stageManager;
    private final Viewport viewport;
    private final StageFactory stageFactory;
    private PlayerManager playerManager;

    public SetColorStage(StageManager stageManager, Viewport viewport, StageFactory stageFactory, PlayerManager playerManager) {
        this.stageManager = stageManager;
        this.viewport = viewport;
        this.stageFactory = stageFactory;
        this.playerManager = playerManager;

        setupBackground();
        setupLayout();
    }

    private void setupBackground() {
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add listener for click on background events.
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(SetColorStage.this);
            }
        });

        this.addActor(background);
    }

    private void setupLayout() {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer table for all widgets */
        Table rootTable = new Table();
        //rootTable.setDebug(true); // turn on all debug lines
        rootTable.setFillParent(true);
        this.addActor(rootTable);

        Label title = new Label("CHOOSE YOUR COLOR", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        rootTable.add(title).padTop(50).padBottom(50);
        rootTable.row();

        /* buttons */
        Set<Color> usedPlayerColors= playerManager.usedPlayerColors();

        /* inner table contains players from lobby: represented as button */
        Table btnTable = new Table();
        int btnHeight = 100;
        int btnWidth = 250;

        /* data rows */
        for( Color color : Colors.getAvailableColors()) {

            /* colors, that are not used by other players, are selectable */
            if(!usedPlayerColors.contains(color)) {
                Button btnColor = new TextButton(color.toString(), skin);
                btnColor.setColor(color);

                btnColor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Log.info("You changed your color to " + color);
                        stageManager.remove(SetColorStage.this);
                        playerManager.setColor(color);
                    }

                });

                btnTable.add(btnColor).height(btnHeight).width(btnWidth);
                btnTable.row();
            }

        }

        /* add buttons */
        rootTable.add(btnTable);

        this.addActor(rootTable);
    }
}