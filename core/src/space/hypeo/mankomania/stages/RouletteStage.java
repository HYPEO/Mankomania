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
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;

public class RouletteStage extends Stage {
    private StageManager stageManager;
    private CheckBox greenField;
    private CheckBox redField;
    private CheckBox blackField;
    private TextField betMoney;
    private Image rouletteImage;
    private Image spinImage;
    private Label wonOrLost;
    private Button close;

    private Random randomizeSpin;
    private int numOfSpins;
    //float spinningDegrees;


    //
    private Button spinRoulette;
    public RouletteStage(Viewport viewport, StageManager stageManager)
    {
        //TODO: Roulette Sprite
        super(viewport);
        String selectedColour = "";
        int money;
        this.stageManager = stageManager;
        randomizeSpin = new Random();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Texture rouletteWheel = new Texture("roulette_wheel.png");
        rouletteImage = new Image(rouletteWheel);
        Texture spinCircle = new Texture("roulette_spincircle.png");
        spinImage = new Image(spinCircle);
        spinRoulette = new TextButton("Spin",skin);
        close = new TextButton("Close",skin);
        blackField = new CheckBox("Black",skin);
        redField = new CheckBox("Red",skin);
        greenField = new CheckBox("Green",skin);

        ButtonGroup betButtons = new ButtonGroup(blackField,redField,greenField);
        //next set the max and min amount to be checked
        betButtons.setMaxCheckCount(1);
        betButtons.setMinCheckCount(0);
        greenField.setName("Green");
        blackField.setName("Black");
        redField.setName("Red");
        Table table = new Table();
        Table buttonTable = new Table();
        Stack overlay = new Stack();
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.align(Align.center);
        spinImage.setSize(266,266);
        rouletteImage.setSize(407,407);
        betMoney = new TextField("", skin);
        wonOrLost = new Label("Good Luck",skin);
        overlay.addActor(spinImage);
        overlay.addActor(rouletteImage);
        //table.setFillParent(true);
        table.add(overlay);
        table.row();
        table.row();
        table.add(buttonTable).padTop(20);
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
        this.addActor(table);
        betMoney.setText("400");
        spinRoulette.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numOfSpins = randomizeSpin.nextInt((184 - 73) + 1) + 73;
                //numOfSpins = 9;
                float spinningDegrees = (360/37) * numOfSpins;

                //numOfSpins = spinningDegrees;
                spinImage.addAction(Actions.parallel(Actions.moveTo(0,0,3), Actions.rotateBy(spinningDegrees, 3)));
                spinImage.setPosition(0, 0);
                spinImage.setOrigin(408/2, 408/2);
                wonOrLost.setText(WinnerIs(Integer.parseInt(betMoney.getText()),betButtons.getChecked().getName()));
                Log.info("Number of Spins: " + numOfSpins);
                spinRoulette.setDisabled(true);
            }
        });
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(RouletteStage.this);
                stageManager.push(StageFactory.getMainMenu(viewport, stageManager));

            }
        });
    }
    public String WinnerIs(Integer money, String selectedColour)
    {
        //TODO: Change Player Balance
        String winningColour;
        if(numOfSpins % 37 == 0)
        {
            winningColour = "Green";
        }
        else if(numOfSpins % 2 == 1 && numOfSpins/37 % 2 == 0)
        {
            winningColour = "Black";
        }
        else if(numOfSpins % 2 == 0 && numOfSpins/37 % 2 == 1)
        {
            winningColour = "Black";
        }
        else if(numOfSpins % 2 == 0 && numOfSpins/37 % 2 == 0)
        {
            winningColour = "Red";
        }
        else if(numOfSpins % 2 == 1 && numOfSpins/37 % 2 == 1)
        {
            winningColour = "Red";
        }
        else
            winningColour = "Error occured";
        if(winningColour.equals(selectedColour))
            return "You Won";
        else if(winningColour.equals("Error occured"))
            return "Error occured";
        else
            return "You Lost";

    }
}
