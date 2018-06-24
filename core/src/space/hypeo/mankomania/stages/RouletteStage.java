package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import java.util.Random;

import space.hypeo.mankomania.DigitFilter;
import space.hypeo.mankomania.Roulette;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.sensor.DiceSensorManager;

public class RouletteStage extends Stage {
    private StageManager stageManager;
    private PlayerActor playerActor;
    private PlayerManager playerManager;
    private StageFactory stageFactory;
    private CheckBox greenField;
    private CheckBox redField;
    private CheckBox blackField;
    private Label betMoney;
    private Image rouletteImage;
    private Image spinImage;
    private Label wonOrLost;
    private Button close;
    private String green;
    private String black;
    private String red;
    private String errorMessage;
    private Table table;
    private Group group;
    private Label playerBalanceLabel;
    private int playerBalance;
    private Skin skin;
    private ButtonGroup betButtons;

    private Random randomizeSpin;
    private int numOfSpins;
    private int money;
    private int maxMoney;
    private Button addMoney;
    private Button removeMoney;
    private Button spinRoulette;
    private Roulette roulette;
    public RouletteStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor)
    {

        //TODO: Roulette Sprite
        super(viewport);
        money  = 0;
        //this.playerManager = playerManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        playerBalance = playerActor.getBalance();
        green = "Green";
        black = "Black";
        red = "Red";
        errorMessage = "Error occured";
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        maxMoney = 50000;

        //Texture rouletteWheel = new Texture("roulette_wheel.png");
        //rouletteImage = new Image(rouletteWheel);
        //Texture spinCircle = new Texture("roulette_spincircle.png");
        //spinImage = new Image(spinCircle);

        setUpRoulette();
        setUpTable();
        this.addActor(table);
        addMoney.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                money = money + 5000;
                if(playerBalance < money)
                    money = playerBalance;
                if(money > maxMoney)
                    money = maxMoney;
                betMoney.setText("Bet Money: " + String.valueOf(money) + "€");
            }
        });
        greenField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(greenField.isChecked())
                    maxMoney = 10000;
                else
                    maxMoney = 50000;
                if(greenField.isChecked() && money > maxMoney)
                {
                    money = maxMoney;
                    betMoney.setText("Bet Money: " + String.valueOf(money) + "€");
                }
            }
        });
        removeMoney.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                money = money - 5000;
                if(money <= 0)
                    money = money + 5000;
                betMoney.setText("Bet Money: " + String.valueOf(money) + "€");

            }
        });
        spinRoulette.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                stageManager.push(stageFactory.getRouletteResultStage(playerActor,true,money,betButtons.getChecked().getName()));
            }
        });
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(RouletteStage.this);
                //stageManager.push(StageFactory.getMainMenu(viewport, stageManager));

            }
        });
    }
    public void setUpRoulette()
    {
        /*group = new Group();
        group.setSize(407,407);
        group.setWidth(this.getWidth());
        group.setHeight(this.getHeight());
        spinImage.setSize(266,266);
        rouletteImage.setSize(407,407);
        group.addActor(rouletteImage);
        group.addActor(spinImage);
        spinImage.setPosition( group.getWidth() / 2f - spinImage.getWidth() / 2f, group.getHeight() / 2f - spinImage.getHeight() / 2f );
        rouletteImage.setPosition( group.getWidth() / 2f - rouletteImage.getWidth() / 2f, group.getHeight() / 2f - rouletteImage.getHeight() / 2f );*/
    }
    public void setUpControlsAndInputs()
    {
        addMoney = new TextButton("+5.000€",skin);
        removeMoney = new TextButton("-5.000€",skin);
        spinRoulette = new TextButton("Spin",skin);
        playerBalanceLabel = new Label("Your balance: " + String.valueOf(playerBalance) + "€",skin);
        close = new TextButton("Close",skin);

        blackField = new CheckBox(black,skin);
        redField = new CheckBox(red,skin);
        greenField = new CheckBox(green,skin);
        greenField.setName(green);
        redField.setName(red);
        blackField.setName(black);

        betButtons = new ButtonGroup(blackField,redField,greenField);
        //next set the max and min amount to be checked
        betButtons.setMaxCheckCount(1);
        betButtons.setMinCheckCount(1);
        blackField.setChecked(true);

        betMoney = new Label("Bet Money: ", skin);
        //wonOrLost = new Label("Good Luck",skin);

    }
    public void setUpTable()
    {
        setUpControlsAndInputs();
        table = new Table();
        table.align(Align.center);
        Table buttonTable = new Table();
        Table addRemoveTable = new Table();
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.add(playerBalanceLabel).align(Align.center);
        table.row();
        table.add(addRemoveTable).padTop(20);
        addRemoveTable.add(addMoney);
        addRemoveTable.add(removeMoney);
        table.row();
        table.add(buttonTable).padTop(5);
        buttonTable.add(blackField).width(100).height(40);
        buttonTable.add(redField).width(100).height(40);
        buttonTable.add(greenField).width(100).height(40);
        table.row();
        table.add(betMoney).width(300).padTop(10);
        table.row();
        table.add(spinRoulette).width(300).height(80).padTop(30);
        table.row();
        table.add(close);
    }


}
