package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.horse.HorseActor;

/**
 * Created by manuelegger on 20.05.18.
 */

public class HorseRaceResultStage extends Stage {
    private StageManager stageManager;
    private int backedHorseID;
    private int bet;

    private Label title;
    private HorseActor winningHorse;
    private Label message;
    private TextButton backButton;

    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    public HorseRaceResultStage(Viewport viewport, final StageManager stageManager, int backedHorseID, int bet,
                                HorseActor winningHorse) {
        super(viewport);

        this.stageManager = stageManager;
        this.backedHorseID = backedHorseID;
        this.bet = bet;
        this.winningHorse = winningHorse;

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
        if(winningHorse.getId() == backedHorseID) {
            title = new Label("Congratulations!! \n" + winningHorse.getHorseName() + " won!", skin);
            message = new Label("Your bet was: " + bet + "\n with a Quote of: " +
                    winningHorse.getQuote() + "\n YOU WIN: " + winningHorse.getQuote() * bet +
                    "Euro", skin);
        }
        else {
            title = new Label("Sorry :( \n" + winningHorse.getHorseName() + " won...", skin);
            message = new Label("You lost your bet of: " + bet + "Euro \n Maybe next time", skin);
        }

        title.setFontScale(2);
        backButton = new TextButton("Back", skin);
    }

    private void setUpClickListeners() {
        backButton.addListener(backButtonListener());
    }

    private void setUpLayout() {
        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(title).width(300).height(100).align(Align.center);
        layout.row();
        layout.add(winningHorse).width(300).height(300);
        layout.row();
        layout.add(message).width(300).height(100);
        layout.row();
        layout.add(backButton).width(200).height(100).padBottom(20);
        layout.row();

        this.addActor(layout);
    }

    private ClickListener backButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(HorseRaceResultStage.this);
            }
        };
    }
}
