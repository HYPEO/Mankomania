package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.horse.HorseActor;

/**
 * Created by manuelegger on 16.05.18.
 */

public class HorseRaceStage extends Stage {
    private StageManager stageManager;
    private Viewport viewport;

    private TextButton horse1Button;
    private TextButton horse2Button;
    private TextButton horse3Button;
    private TextButton horse4Button;

    private HorseActor horse1Actor;
    private HorseActor horse2Actor;
    private HorseActor horse3Actor;
    private HorseActor horse4Actor;

    private MoveToAction horse1Movement;
    private MoveToAction horse2Movement;
    private MoveToAction horse3Movement;
    private MoveToAction horse4Movement;

    private TextButton startRace;
    private Label currentAmount;
    private float selectedHorseQuote;
    private int slectedHorseID;
    private int winningHorseID;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    final Slider amount = new Slider(5000, 50000, 1000, false, skin);


    public HorseRaceStage(Viewport viewport, StageManager stageManager) {
        super(viewport);
        this.stageManager = stageManager;
        this.viewport = viewport;
        selectedHorseQuote = 0;

        // Set up Stage
        setUpBackground();
        createWidgets();
        setUpClickListeners();
        setUpLayout();

        //decide which horse is going to win an how fast the other ones are going
        setUpRaceResult();
    }

    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }

    private void createWidgets() {
        horse1Actor = new HorseActor(1, "La Tartaruga", 1f);
        horse2Actor = new HorseActor(2, "Schnecki", 1.5f);
        horse3Actor = new HorseActor(3, "Salami", 1.8f);
        horse4Actor = new HorseActor(4, "Plumbum", 2.2f);

        horse1Button = new TextButton(horse1Actor.getHorseName() + "\n Quote: " +
                Float.toString(horse1Actor.getQuote()), skin);
        horse2Button = new TextButton(horse2Actor.getHorseName() + "\n Quote: " +
                Float.toString(horse2Actor.getQuote()), skin);
        horse3Button = new TextButton(horse3Actor.getHorseName() + "\n Quote: " +
                Float.toString(horse3Actor.getQuote()), skin);
        horse4Button = new TextButton(horse4Actor.getHorseName() + "\n Quote: " +
                Float.toString(horse4Actor.getQuote()), skin);

        startRace = new TextButton("GO!!!", skin);
        currentAmount = new Label("5000 Euro", skin);
    }

    private void setUpClickListeners() {
        horse1Button.addListener(horse1ClickListener());
        horse2Button.addListener(horse2ClickListener());
        horse3Button.addListener(horse3ClickListener());
        horse4Button.addListener(horse4ClickListener());

        startRace.addListener(startRaceClickListender());
        amount.addListener(amountChangeListener());
    }

    private void setUpLayout() {
        horse1Actor.setPosition(0, this.getHeight() - horse1Actor.getHeight());
        this.addActor(horse1Actor);

        horse2Actor.setPosition(0, this.getHeight() - horse2Actor.getHeight() * 2);
        this.addActor(horse2Actor);

        horse3Actor.setPosition(0, this.getHeight() - horse3Actor.getHeight() * 3);
        this.addActor(horse3Actor);

        horse4Actor.setPosition(0, this.getHeight() - horse4Actor.getHeight() * 4);
        this.addActor(horse4Actor);

        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(horse1Button).width(100).height(100).padRight(10).padLeft(5);
        layout.add(horse2Button).width(100).height(100).padRight(10);
        layout.add(horse3Button).width(100).height(100).padRight(10);
        layout.add(horse4Button).width(100).height(100).padRight(10);
        layout.row();
        layout.add(amount).colspan(4).width(300).padTop(40 - amount.getHeight());
        layout.row();
        layout.add();
        layout.add(currentAmount).colspan(2).padBottom(40 - currentAmount.getHeight());
        layout.row();
        layout.add(startRace).colspan(4).width(350).height(100).padBottom(15);

        this.addActor(layout);
    }

    private void setUpRaceResult() {
        Random rand = new Random();
        float minTime = 1.5f;
        float maxTime = 3.5f; // note for adjustments: maxTime <= maxTime - minTime
        float avgQuoteTime = (horse1Actor.getQuote() + horse2Actor.getQuote() + horse3Actor.getQuote() +
                horse4Actor.getQuote()) / 4;
        float horse1Time;
        float horse2Time;
        float horse3Time;
        float horse4Time;

        // Calculate time for each horse and avoid horses with same time
        do {
            horse1Time = ((rand.nextFloat() * maxTime + minTime) * horse1Actor.getQuote()) / avgQuoteTime;
            horse2Time = ((rand.nextFloat() * maxTime + minTime) * horse1Actor.getQuote()) / avgQuoteTime;
            horse3Time = ((rand.nextFloat() * maxTime + minTime) * horse1Actor.getQuote()) / avgQuoteTime;
            horse4Time = ((rand.nextFloat() * maxTime + minTime) * horse1Actor.getQuote()) / avgQuoteTime;
        } while (!checkUniqueHorseTimes(horse1Time, horse2Time, horse3Time, horse4Time));

        calculateWinningHorse(horse1Time, horse2Time, horse3Time, horse4Time);

        // Create MoveToActions for each horse with it's time
        // Horse 1
        horse1Movement = new MoveToAction();
        horse1Movement.setPosition(this.getWidth() - horse1Actor.getWidth(),
                this.getHeight() - horse1Actor.getHeight());
        horse1Movement.setDuration(horse1Time);
        horse1Movement.setInterpolation(Interpolation.fade);

        // Horse 2
        horse2Movement = new MoveToAction();
        horse2Movement.setPosition(this.getWidth() - horse2Actor.getWidth(),
                this.getHeight() - horse2Actor.getHeight() * 2);
        horse2Movement.setDuration(horse2Time);
        horse2Movement.setInterpolation(Interpolation.fade);

        // Horse 3
        horse3Movement = new MoveToAction();
        horse3Movement.setPosition(this.getWidth() - horse2Actor.getWidth(),
                this.getHeight() - horse3Actor.getHeight() * 3);
        horse3Movement.setDuration(horse3Time);
        horse3Movement.setInterpolation(Interpolation.fade);

        // Horse 4
        horse4Movement = new MoveToAction();
        horse4Movement.setPosition(this.getWidth() - horse2Actor.getWidth(),
                this.getHeight() - horse4Actor.getHeight() * 4);
        horse4Movement.setDuration(horse4Time);
        horse4Movement.setInterpolation(Interpolation.fade);
    }

    private void calculateWinningHorse(float horse1, float horse2, float horse3, float horse4) {
        if(horse1 < horse2) {
            if (horse3 < horse4) {
                if (horse1 < horse3) {
                    winningHorseID = 1;
                }
                else {
                    winningHorseID = 3;
                }
            }
            else {
                if (horse1 < horse4) {
                    winningHorseID = 1;
                }
                else {
                    winningHorseID = 4;
                }
            }
        }
        else {
            if (horse3 < horse4) {
                if (horse2 < horse3) {
                    winningHorseID = 2;
                }
                else {
                    winningHorseID = 3;
                }
            }
            else {
                if (horse2 < horse4) {
                    winningHorseID = 2;
                }
                else {
                    winningHorseID = 4;
                }
            }
        }
    }

    private boolean checkUniqueHorseTimes(float horse1, float horse2, float horse3, float horse4) {
        if(horse1 != horse2 && horse1 != horse3 && horse1 != horse4 && horse2 != horse3 &&
                horse2 != horse4 && horse3 != horse4) {
            return true;
        }
        else {
            return false;
        }
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
                if(selectedHorseQuote != 0) {
                    if (!startRace.getLabel().textEquals("get Results")) {
                        horse1Actor.addAction(horse1Movement);
                        horse2Actor.addAction(horse2Movement);
                        horse3Actor.addAction(horse3Movement);
                        horse4Actor.addAction(horse4Movement);

                        startRace.setText("get Results");
                    } else {
                        // remove this Stage so you get back to the game after Result Stage is closed
                        stageManager.remove(HorseRaceStage.this);

                        // push ResultStage
                        stageManager.push(StageFactory.getHorseRaceResultStage(viewport, stageManager,
                                slectedHorseID, ((int) amount.getValue()), getWinningHorse(winningHorseID)));
                    }
                } else {
                    startRace.setText("Select a Horse and GO!!");
                }
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
        horse1Button.getLabel().setColor(Color.WHITE);
        horse2Button.getLabel().setColor(Color.WHITE);
        horse3Button.getLabel().setColor(Color.WHITE);
        horse4Button.getLabel().setColor(Color.WHITE);
        slectedHorseID = horseID;

        switch (horseID) {
            case 1: horse1Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorseQuote = horse1Actor.getQuote();
                    break;
            case 2: horse2Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorseQuote = horse1Actor.getQuote();
                    break;
            case 3: horse3Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorseQuote = horse3Actor.getQuote();
                    break;
            case 4: horse4Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
                    selectedHorseQuote = horse4Actor.getQuote();
                    break;
        }
    }

    private HorseActor getWinningHorse(int horseID) {
        switch (horseID) {
            case 1: return horse1Actor;
            case 2: return horse2Actor;
            case 3: return horse3Actor;
            case 4: return horse4Actor;
        }
        return null;
    }
}
