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

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.actors.symbol.SymbolActor;
import space.hypeo.mankomania.game.SlotMachineLogic;

/**
 * Created by manuelegger on 05.06.18.
 */

public class SlotMachineStage extends Stage {
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;
    private SlotMachineLogic slotMachineLogic;

    private TextButton startGameButton;
    private TextButton exitGameButton;
    private Label playerInfo;

    private SymbolActor symbol1Actor;
    private SymbolActor symbol2Actor;
    private SymbolActor symbol3Actor;
    private int symbol1ID;
    private int symbol2ID;
    private int symbol3ID;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


    public SlotMachineStage (Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        slotMachineLogic = new SlotMachineLogic();

        symbol1ID = slotMachineLogic.getSymbol1();
        symbol2ID = slotMachineLogic.getSymbol2();
        symbol3ID = slotMachineLogic.getSymbol3();

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
        startGameButton.setWidth(200);
        exitGameButton = new TextButton("exit Game", skin);
        exitGameButton.setWidth(200);
        playerInfo = new Label("By playing with the slotmachine you have to bet 25.000", skin);
    }
    private void setUpClickListeners() {
        startGameButton.addListener(startGameClickListener());
        exitGameButton.addListener(exitGameClickListener());
    }
    private void setUpLayout() {
        //show fist symbol 3 times before game start
        symbol1Actor = new SymbolActor(1);
        symbol1Actor.setPosition(this.getWidth() / 2 - symbol1Actor.getWidth() / 2 - symbol1Actor.getWidth(),
                this.getHeight() - symbol1Actor.getHeight() * 2.5f);
        this.addActor(symbol1Actor);

        symbol1Actor = new SymbolActor(1);
        symbol1Actor.setPosition(this.getWidth() / 2 - symbol1Actor.getWidth() / 2,
                this.getHeight() - symbol1Actor.getHeight() * 2.5f);
        this.addActor(symbol1Actor);

        symbol1Actor = new SymbolActor(1);
        symbol1Actor.setPosition(this.getWidth() / 2 - symbol1Actor.getWidth() / 2 + symbol1Actor.getWidth(),
                this.getHeight() - symbol1Actor.getHeight() * 2.5f);
        this.addActor(symbol1Actor);

        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(playerInfo).colspan(2).padBottom(10);
        layout.row();
        layout.add(startGameButton).width(200).height(100).padRight(10).padLeft(5).padBottom(30);
        layout.add(exitGameButton).width(200).height(100).padRight(10).padBottom(30);
        layout.row();

        this.addActor(layout);
    }
    private void setUpResultLayout() {
        this.clear();
        setUpBackground();
        createWidgets();
        setUpClickListeners();

        symbol1Actor = new SymbolActor(symbol1ID);
        symbol1Actor.setPosition(this.getWidth() / 2 - symbol1Actor.getWidth() / 2 - symbol1Actor.getWidth(),
                this.getHeight() - symbol1Actor.getHeight() * 2.5f);
        this.addActor(symbol1Actor);

        symbol2Actor = new SymbolActor(symbol2ID);
        symbol2Actor.setPosition(this.getWidth() / 2 - symbol2Actor.getWidth() / 2,
                this.getHeight() - symbol2Actor.getHeight() * 2.5f);
        this.addActor(symbol2Actor);

        symbol3Actor = new SymbolActor(symbol3ID);
        symbol3Actor.setPosition(this.getWidth() / 2 - symbol3Actor.getWidth() / 2 + symbol3Actor.getWidth(),
                this.getHeight() - symbol3Actor.getHeight() * 2.5f);
        this.addActor(symbol3Actor);

        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(startGameButton).width(400).height(100).padRight(10).padLeft(5).padBottom(30);
        layout.row();

        this.addActor(layout);
    }

    private ClickListener startGameClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (startGameButton.getLabel().textEquals("Start Game")) {
                    playerActor.changeBalance(-25000);

                    setUpResultLayout();
                    startGameButton.setText("get Results");
                }
                else {
                    playerActor.changeBalance(slotMachineLogic.getPrice());

                    stageManager.remove(SlotMachineStage.this);
                    stageManager.push(stageFactory.getSlotMachineResultStage(playerActor, slotMachineLogic));
                }
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
