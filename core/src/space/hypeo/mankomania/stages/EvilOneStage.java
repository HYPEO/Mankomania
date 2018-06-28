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

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.game.EvilOneLogic;

public class EvilOneStage extends Stage {
    private PlayerActor playerActor;
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
    private int diced1;
    private int diced2;
    private EvilOneLogic evilOne;

    /**
     * Creates Stage of EvilOne
     * @param viewport
     * @param stageManager
     * @param playerActor
     */
    public EvilOneStage(Viewport viewport, StageManager stageManager, PlayerActor playerActor)
    {

        super(viewport);
        random = new Random();
        this.playerActor = playerActor;
        evilOne = new EvilOneLogic(playerActor);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        dice1Texture = new Texture("fields/evil-one.png");
        dice1Image = new Image(dice1Texture);
        dice2Texture = new Texture("fields/evil-one.png");
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
                dice1Image.setDrawable(new SpriteDrawable(new Sprite(new Texture("dice/dice" + diced1 + ".png"))));
                dice2Image.setDrawable(new SpriteDrawable(new Sprite(new Texture("dice/dice" + diced2 + ".png"))));
                if(evilOne.checkDiceResults(diced1,diced2))
                {
                    currentState.setText("You currently loose "+evilOne.getTotalDiceMoney() + "€");
                }
                else
                {
                    diceButton.remove();
                    stopDice.remove();
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
                diceButton.remove();
                stopDice.remove();
                currentState.setText("You lost " + evilOne.getTotalDiceMoney() + "€... pls wait");
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
    /**
     * Creates Background in our pink colour
     */
    private void setUpBackground()
    {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
        this.addActor(background);
    }
    /**
     * Sets Up the Table with the Images Buttons and Labels
     */
    private void setUpDiceTable()
    {
        table = new Table();
        table.align(Align.center);
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.add(infoLabel);
        table.row();
        table.add(playerBalance).padTop(10).padBottom(30);
        table.row();

        table.add(dice1Image).padRight(10).size(150,150);
        table.add(dice2Image).padLeft(10).size(150,150);
        table.row();
        table.add(currentState).padTop(30);
        table.row();
        table.add(diceButton).height(70).width(150).padTop(10);
        table.add(stopDice).height(70).width(150).padTop(10);

    }
    /**
     * Initializes Buttons and Labels
     */
    private void setUpButtons()
    {
        diceButton = new TextButton("Dice",skin);
        stopDice = new TextButton("Enough diced",skin);
        infoLabel = new Label("If you get a 1 you win 50.000€",skin);
        playerBalance = new Label("Current Player Balance: " + playerActor.getBalance() + "€",skin);
        currentState = new Label("Currently happens nothing",skin);


    }

}
