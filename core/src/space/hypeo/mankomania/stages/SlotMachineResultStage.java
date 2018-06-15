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
import space.hypeo.mankomania.actors.symbol.SymbolActor;
import space.hypeo.mankomania.game.SlotMachineLogic;

/**
 * Created by manuelegger on 14.06.18.
 */

public class SlotMachineResultStage extends Stage {
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;
    private SlotMachineLogic slotMachineLogic;

    private TextButton backButton;
    private Label resultInfo;

    private SymbolActor symbol1Actor;
    private SymbolActor symbol2Actor;
    private SymbolActor symbol3Actor;
    private int symbol1ID;
    private int symbol2ID;
    private int symbol3ID;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


    public SlotMachineResultStage (Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor,
                                   SlotMachineLogic slotMachineLogic) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        this.slotMachineLogic = slotMachineLogic;

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
        backButton = new TextButton("Back", skin);

        if (slotMachineLogic.isGameWon()) {
            resultInfo = new Label("Congratulations you got the " + slotMachineLogic.getPriceType() + "\n and won "
                    + slotMachineLogic.getPrice(), skin);
        } else {
            resultInfo = new Label("We are very sorry you lost your bet of 25.000", skin);
        }

    }

    private void setUpClickListeners() {
        backButton.addListener(backClickListener());
    }

    private void setUpLayout() {
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
        layout.add(resultInfo).padBottom(10);
        layout.row();
        layout.add(backButton).width(400).height(100).padRight(10).padBottom(30);
        layout.row();

        this.addActor(layout);
    }

    private ClickListener backClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(SlotMachineResultStage.this);
            }
        };
    }
}
