package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import space.hypeo.mankomania.Roulette;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.player.PlayerManager;

public class RouletteResultStage extends Stage {
    private StageManager stageManager;
    private PlayerActor playerActor;
    private PlayerManager playerManager;
    private StageFactory stageFactory;
    private int money;
    private String selectedColour;
    private CheckBox greenField;
    private CheckBox redField;
    private CheckBox blackField;
    private TextField betMoney;
    private Image rouletteImage;
    private Image spinImage;
    private Label wonOrLost;
    private Button buttonCheater;
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
    private Roulette roulette;
    private boolean cheated;

    public RouletteResultStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor, int numOfSpins, boolean cheated, int money){
        super(viewport);
        this.cheated = cheated;
        //this.playerManager = playerManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        this.stageManager = stageManager;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.numOfSpins = numOfSpins;
        setUpRoulette();
        setUpRoulette();
        Texture rouletteWheel = new Texture("roulette_wheel.png");
        rouletteImage = new Image(rouletteWheel);
        Texture spinCircle = new Texture("roulette_spincircle.png");
        spinImage = new Image(spinCircle);

        float spinningDegrees = (360/37) * numOfSpins;
        roulette = new Roulette(playerActor, numOfSpins);
        spinImage.addAction(Actions.parallel(Actions.moveTo(group.getWidth() / 2f - spinImage.getWidth() / 2f, group.getHeight() / 2f - spinImage.getHeight() / 2f ,3), Actions.rotateBy(spinningDegrees, 0)));
        spinImage.setOrigin(266/2f, 266/2f);


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
        table.row();
        table.row();
        setUpButtons();
        table.add(buttonCheater).width(300).height(80).padTop(30);
        table.add(close).width(300).height(80).padTop(30);
    }
    public void setUpButtons()
    {
        buttonCheater = new TextButton("Cheater",skin);
        close = new TextButton("Close",skin);
    }


}
