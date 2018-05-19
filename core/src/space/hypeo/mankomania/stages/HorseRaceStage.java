package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

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
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        horse1 = new TextButton("La Tartaruga \n Quote: 1", skin);
        horse2 = new TextButton("Schnecki \n Quote: 1,5", skin);
        horse3 = new TextButton("Salami \n Quote: 2", skin);
        horse4 = new TextButton("Plumbum \n Quote: 2,5", skin);
        startRace = new TextButton("GO!!!", skin);

    }

    private void setUpClickListeners() {
        horse1.addListener(horse1ClickListener());
        horse2.addListener(horse1ClickListener());
        horse3.addListener(horse1ClickListener());
        horse4.addListener(horse1ClickListener());
        startRace.addListener(horse1ClickListener());
    }

    private void setUpLayout() {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label setAmountPlaceholder = new Label("set amount here", skin);

        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.bottom);
        layout.setPosition(0, 0);
        layout.add(horse1).width(100).height(100).padRight(10).padLeft(5);
        layout.add(horse2).width(100).height(100).padRight(10);
        layout.add(horse3).width(100).height(100).padRight(10);
        layout.add(horse4).width(100).height(100).padRight(10);
        layout.row();
        layout.add(setAmountPlaceholder).width(100).height(100);
        layout.row();
        layout.add(startRace).colspan(4).width(350).height(100).padBottom(50);

        this.addActor(layout);
    }

    private ClickListener horse1ClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(HorseRaceStage.this);
            }
        };
    }
}
