package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import java.util.Random;

import space.hypeo.mankomania.DigitFilter;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.player.PlayerManager;

public class RouletteStage extends Stage {
    private StageManager stageManager;
    private PlayerActor playerActor;
    private PlayerManager playerManager;
    private StageFactory stageFactory;
    private CheckBox greenField;
    private CheckBox redField;
    private CheckBox blackField;
    private TextField betMoney;
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
    private Skin skin;
    private ButtonGroup betButtons;

    private Random randomizeSpin;
    private int numOfSpins;

    private Button spinRoulette;
    public RouletteStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor)
    {

        //TODO: Roulette Sprite
        super(viewport);
        //this.playerManager = playerManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        green = "Green";
        black = "Black";
        red = "Red";
        errorMessage = "Error occured";
        this.stageManager = stageManager;
        randomizeSpin = new Random();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Texture rouletteWheel = new Texture("roulette_wheel.png");
        rouletteImage = new Image(rouletteWheel);
        Texture spinCircle = new Texture("roulette_spincircle.png");
        spinImage = new Image(spinCircle);

        setUpRoulette();
        setUpTable();

        this.addActor(table);
        spinRoulette.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numOfSpins = randomizeSpin.nextInt((184 - 73) + 1) + 73;
                float spinningDegrees = (360/37) * numOfSpins;

                spinImage.addAction(Actions.parallel(Actions.moveTo(group.getWidth() / 2f - spinImage.getWidth() / 2f, group.getHeight() / 2f - spinImage.getHeight() / 2f ,3), Actions.rotateBy(spinningDegrees, 3)));
                spinImage.setOrigin(266/2f, 266/2f);

                wonOrLost.setText(getResult(Integer.parseInt(betMoney.getText()),betButtons.getChecked().getName()));
                Log.info("Number of Spins: " + numOfSpins);
                spinRoulette.setDisabled(true);
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
        group = new Group();
        group.setSize(407,407);
        group.setWidth(this.getWidth());
        group.setHeight(this.getHeight());
        spinImage.setSize(266,266);
        rouletteImage.setSize(407,407);
        group.addActor(rouletteImage);
        group.addActor(spinImage);
        spinImage.setPosition( group.getWidth() / 2f - spinImage.getWidth() / 2f, group.getHeight() / 2f - spinImage.getHeight() / 2f );
        rouletteImage.setPosition( group.getWidth() / 2f - rouletteImage.getWidth() / 2f, group.getHeight() / 2f - rouletteImage.getHeight() / 2f );
    }
    public void setUpControlsAndInputs()
    {
        spinRoulette = new TextButton("Spin",skin);
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

        betMoney = new TextField("", skin);
        betMoney.setTextFieldFilter(new DigitFilter());
        wonOrLost = new Label("Good Luck",skin);

    }
    public void setUpTable()
    {
        table = new Table();
        table.align(Align.center);
        Table buttonTable = new Table();
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.add(group).padBottom(-200).padTop(-100);
        table.row();
        table.add(buttonTable).padTop(5);
        setUpControlsAndInputs();
        buttonTable.add(blackField).width(100).height(40);
        buttonTable.add(redField).width(100).height(40);
        buttonTable.add(greenField).width(100).height(40);
        table.row();
        table.add(betMoney).width(300).padTop(10);
        table.row();
        table.add(wonOrLost);
        table.row();
        table.add(spinRoulette).width(300).height(80).padTop(30);
        table.row();
        table.add(close);
    }
    public String getResult(Integer money, String selectedColour)
    {
        //TODO: Change Player Balance
        String winningColour;
        int factor = 0;
        if(numOfSpins % 37 == 0)
        {
            winningColour = green;
            factor = 14;
        }
        else if((numOfSpins % 2 == 1 && numOfSpins/37 % 2 == 0) || (numOfSpins % 2 == 0 && numOfSpins/37 % 2 == 1))
        {
            winningColour = black;
            factor = 2;
        }
        else if((numOfSpins % 2 == 0 && numOfSpins/37 % 2 == 0) || (numOfSpins % 2 == 1 && numOfSpins/37 % 2 == 1))
        {
            winningColour = red;
            factor = 2;
        }
        else
            winningColour = errorMessage;
        if(winningColour.equals(selectedColour)) {
            //playerActor.changeBalance(money * factor);
            return "You Won";

        }
        else if(winningColour.equals(errorMessage))
            return errorMessage;
        else {
            //playerActor.changeBalance(money * (-1));
            return "You Lost";
        }

    }

}
