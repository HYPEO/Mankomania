package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by manuelegger on 05.06.18.
 */

public class SlotMachineStage extends Stage {
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;

    private TextButton startGameButton;
    private TextButton exitGameButton;
    private Label playerInfo;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


    public SlotMachineStage (Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;

        // Set up Stage
        setUpBackground();
        createWidgets();
        setUpClickListeners();
        setUpLayout();
    }

    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }
    private void createWidgets() {
        startGameButton = new TextButton("Start Game", skin);
        exitGameButton = new TextButton("exit Game", skin);
        playerInfo = new Label("By playing with the slotmachine you have to bet 25.000", skin);
    }
    private void setUpClickListeners() {
        startGameButton.addListener(startGameClickListener());
        exitGameButton.addListener(exitGameClickListener());
    }
    private void setUpLayout() {
        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(playerInfo).colspan(2).padBottom(10);
        layout.row();
        layout.add(startGameButton).width(200).height(100).padRight(10).padLeft(5).padBottom(10);
        layout.add(exitGameButton).width(200).height(100).padRight(10).padBottom(10);
        layout.row();

        this.addActor(layout);
    }


    private ClickListener startGameClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //start Game
            }
        };
    }
    private ClickListener exitGameClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(SlotMachineStage.this);
            }
        };
    }
}
