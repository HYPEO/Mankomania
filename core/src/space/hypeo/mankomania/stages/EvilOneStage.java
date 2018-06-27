package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.game.EvilOneLogic;
import space.hypeo.mankomania.sensor.DiceSensorManager;

public class EvilOneStage extends Stage {
    private PlayerActor playerActor;
    private StageFactory stageFactory;
    private DiceSensorManager diceSensorManager;
    private StageManager stageManager;
    private Table table;
    private Button diceButton;
    private Button stopDice;
    private Skin skin;
    private Image dice1Image;
    private Image dice2Image;
    private Texture dice1Texture;
    private Texture dice2Texture;
    private Label infoLabel;
    private Label playerBalance;
    private Label currentState;
    private Random random;
    private int totalNumberOfSpots;
    private int diced1;
    private int diced2;
    private EvilOneLogic evilOne;

    public EvilOneStage(Viewport viewport, StageManager stageManager, PlayerActor playerActor)
    {

        super(viewport);
        random = new Random();
        this.stageManager = stageManager;
        this.playerActor = playerActor;
        evilOne = new EvilOneLogic(playerActor);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        dice1Texture = new Texture("dice/dice6.png");
        dice1Image = new Image(dice1Texture);
        dice2Texture = new Texture("dice/dice1.png");
        dice2Image = new Image(dice2Texture);
        setUpBackground();
        setUpButtons();
        setUpDiceTable();

        this.addActor(table);

        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diced1 = random.nextInt(6) + 1;
                diced2 = random.nextInt(6) + 1;
                dice1Image.setDrawable(new SpriteDrawable(new Sprite(new Texture("dice/dice" + String.valueOf(diced1) + ".png"))));
                dice2Image.setDrawable(new SpriteDrawable(new Sprite(new Texture("dice/dice" + String.valueOf(diced2) + ".png"))));
                if(evilOne.checkDiceResults(diced1,diced2))
                {
                    currentState.setText("You currently loose "+evilOne.getTotalDiceMoney() + "€");
                }
                else
                {
                    diceButton.setDisabled(true);
                    stopDice.setDisabled(true);
                    if(evilOne.getTotalDiceMoney() == -100000)
                        currentState.setText("Oh no a 1 you win 100.000€... pls wait");
                    else
                        currentState.setText("Oh no both 1 you win 300.000€... pls wait");
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            stageManager.remove(EvilOneStage.this);
                        }
                    }, 7);

                }
            }
        });
        stopDice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diceButton.setDisabled(true);
                stopDice.setDisabled(true);
                currentState.setText("You lost " + String.valueOf(evilOne.getTotalDiceMoney()) + "€... pls wait");
                evilOne.updatePlayerBalance();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        stageManager.remove(EvilOneStage.this);
                    }
                }, 7);
            }
        });
    }
    private void setUpBackground()
    {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
        this.addActor(background);
    }
    private void setUpDiceTable()
    {
        table = new Table();
        //HorizontalGroup diceGroup = new HorizontalGroup();
        table.align(Align.center);
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.add(infoLabel);
        table.row();
        table.add(playerBalance);
        table.row();

        table.add(dice1Image).padRight(10).size(150,150);
        table.add(dice2Image).padLeft(10).size(150,150);
        table.row();
        table.add(currentState);
        table.row();
        table.add(diceButton).height(30).width(100);
        table.row();
        table.add(stopDice).height(30).width(100);

    }
    private void setUpButtons()
    {
        diceButton = new TextButton("Dice",skin);
        stopDice = new TextButton("Enough diced",skin);
        infoLabel = new Label("If you get a 1 you win 50.000€",skin);
        playerBalance = new Label("Current Player Balance: " + String.valueOf(playerActor.getBalance()) + "€",skin);
        currentState = new Label("Currently happens nothing",skin);


    }

}
