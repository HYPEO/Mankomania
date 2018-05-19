package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.String;

import javax.xml.soap.Text;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;

/**
 * Created by manuelegger on 16.05.18.
 */

public class HorseRaceStage extends Stage {
    private StageManager stageManager;
    private Viewport viewport;

    private TextButton horse1;
    private TextButton horse2;
    private TextButton horse3;
    private TextButton horse4;
    private TextButton startRace;
    private Label currentAmount;
    private float selectedHorse; //selectedHorse => Horse Quote
    private float horse1Quote = 1f;
    private float horse2Quote = 1.5f;
    private float horse3Quote = 2f;
    private float horse4Quote = 2.5f;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    final Slider amount = new Slider(5000, 50000, 1000, false, skin);


    public HorseRaceStage(Viewport viewport, StageManager stageManager) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;

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
        horse1 = new TextButton("La Tartaruga \n Quote: " + Float.toString(horse1Quote), skin);
        horse2 = new TextButton("Schnecki \n Quote: " + Float.toString(horse1Quote), skin);
        horse3 = new TextButton("Salami \n Quote: " + Float.toString(horse1Quote), skin);
        horse4 = new TextButton("Plumbum \n Quote: " + Float.toString(horse1Quote), skin);
        startRace = new TextButton("GO!!!", skin);
        currentAmount = new Label("5000 Euro", skin);

    }

    private void setUpClickListeners() {
        horse1.addListener(horse1ClickListener());
        horse2.addListener(horse2ClickListener());
        horse3.addListener(horse3ClickListener());
        horse4.addListener(horse4ClickListener());
        startRace.addListener(startRaceClickListender());
        amount.addListener(amountChangeListener());
    }

    private void setUpLayout() {
        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(horse1).width(100).height(100).padRight(10).padLeft(5);
        layout.add(horse2).width(100).height(100).padRight(10);
        layout.add(horse3).width(100).height(100).padRight(10);
        layout.add(horse4).width(100).height(100).padRight(10);
        layout.row();
        layout.add(amount).colspan(4).width(300).padTop(50 - amount.getHeight());
        layout.row();
        layout.add();
        layout.add(currentAmount).colspan(2).padBottom(50 - currentAmount.getHeight());
        layout.row();
        layout.add(startRace).colspan(4).width(350).height(100).padBottom(50);

        this.addActor(layout);
    }

    private ClickListener horse1ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectHorse(1);
            }
        };
    }
    private ClickListener horse2ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectHorse(2);
            }
        };
    }
    private ClickListener horse3ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectHorse(3);
            }
        };
    }
    private ClickListener horse4ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectHorse(4);
            }
        };
    }
    private ClickListener startRaceClickListender() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // start race with selectedHorse
                stageManager.remove(HorseRaceStage.this);
            }
        };
    }

    private ChangeListener amountChangeListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentAmount.setText(Float.toString(amount.getValue()).substring(0,
                        Float.toString(amount.getValue()).length() - 2) + " Euro");
            }
        };
    }

    private void selectHorse(int horseID) {
        horse1.getLabel().setColor(Color.WHITE);
        horse2.getLabel().setColor(Color.WHITE);
        horse3.getLabel().setColor(Color.WHITE);
        horse4.getLabel().setColor(Color.WHITE);

        switch (horseID) {
            case 1: horse1.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorse = horse1Quote;
                    break;
            case 2: horse2.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorse = horse2Quote;
                    break;
            case 3: horse3.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorse = horse3Quote;
                    break;
            case 4: horse4.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorse = horse4Quote;
                    break;
        }
    }
}
