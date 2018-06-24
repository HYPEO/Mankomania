package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.game.HorseRaceStageLogic;

/**
 * Created by manuelegger on 16.05.18.
 */

public class HorseRaceStage extends Stage {
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;
    private HorseRaceStageLogic raceLogic;

    private TextButton horse1Button;
    private TextButton horse2Button;
    private TextButton horse3Button;
    private TextButton horse4Button;
    private TextButton startRace;
    private Label currentAmount;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    final Slider amount = new Slider(5000, 50000, 1000, false, skin);


    public HorseRaceStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        raceLogic = new HorseRaceStageLogic(this.getHeight(), this.getWidth());

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
        final String quote = "\n Quote: ";

        horse1Button = new TextButton(raceLogic.getHorse1().getHorseName() + quote +
                Float.toString(raceLogic.getHorse1().getQuote()), skin);
        horse2Button = new TextButton(raceLogic.getHorse2().getHorseName() + quote +
                Float.toString(raceLogic.getHorse2().getQuote()), skin);
        horse3Button = new TextButton(raceLogic.getHorse3().getHorseName() + quote +
                Float.toString(raceLogic.getHorse3().getQuote()), skin);
        horse4Button = new TextButton(raceLogic.getHorse4().getHorseName() + quote +
                Float.toString(raceLogic.getHorse4().getQuote()), skin);

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
        raceLogic.getHorse1().setPosition(0, this.getHeight() - raceLogic.getHorse1().getHeight());
        this.addActor(raceLogic.getHorse1());

        raceLogic.getHorse2().setPosition(0, this.getHeight() - raceLogic.getHorse2().getHeight() * 2);
        this.addActor(raceLogic.getHorse2());

        raceLogic.getHorse3().setPosition(0, this.getHeight() - raceLogic.getHorse3().getHeight() * 3);
        this.addActor(raceLogic.getHorse3());

        raceLogic.getHorse4().setPosition(0, this.getHeight() - raceLogic.getHorse4().getHeight() * 4);
        this.addActor(raceLogic.getHorse4());

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

    private ClickListener horse1ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                raceLogic.setSelectedHorse(1);

                clearButtonFormatting();
                horse1Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
            }
        };
    }
    private ClickListener horse2ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                raceLogic.setSelectedHorse(2);

                clearButtonFormatting();
                horse2Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
            }
        };
    }
    private ClickListener horse3ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                raceLogic.setSelectedHorse(3);

                clearButtonFormatting();
                horse3Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
            }
        };
    }
    private ClickListener horse4ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                raceLogic.setSelectedHorse(4);

                clearButtonFormatting();
                horse4Button.getLabel().setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);
            }
        };
    }

    private ClickListener startRaceClickListender() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(raceLogic.getSelectedHorseQuote() != 0) {
                    if (!startRace.getLabel().textEquals("get Results")) {
                        raceLogic.getHorse1().addAction(raceLogic.getHorse1Movement());
                        raceLogic.getHorse2().addAction(raceLogic.getHorse2Movement());
                        raceLogic.getHorse3().addAction(raceLogic.getHorse3Movement());
                        raceLogic.getHorse4().addAction(raceLogic.getHorse4Movement());

                        startRace.setText("get Results");
                    } else {
                        // calculate race result and change the player's balance
                        if(raceLogic.getWinningHorse().getId() == raceLogic.getSelectedHorse()) {
                            // player won, increase balance
                            playerActor.changeBalance(Math.round(amount.getValue() * raceLogic.getSelectedHorseQuote()));
                        }
                        else {
                            // player lost, reduce balance
                            playerActor.changeBalance((int) amount.getValue() * -1);
                        }

                        // remove this Stage so you get back to the game after Result Stage is closed
                        stageManager.remove(HorseRaceStage.this);

                        // push ResultStage
                        stageManager.push(stageFactory.getHorseRaceResultStage(
                                raceLogic.getSelectedHorse(), ((int) amount.getValue()), raceLogic.getWinningHorse()));
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

    private void clearButtonFormatting() {
        horse1Button.getLabel().setColor(Color.WHITE);
        horse2Button.getLabel().setColor(Color.WHITE);
        horse3Button.getLabel().setColor(Color.WHITE);
        horse4Button.getLabel().setColor(Color.WHITE);
    }
}
