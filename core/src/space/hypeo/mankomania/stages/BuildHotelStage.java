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

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.fields.BuildHotel;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.game.EconomicStageLogic;

public class BuildHotelStage extends Stage {
    private StageManager stageManager;
    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    private int kosten = 1000;
    private EconomicStageLogic eco;
    private BuildHotel build;



    public BuildHotelStage(Viewport viewport, StageManager stageManager, PlayerActor playerActor,BuildHotel build) {
        super(viewport);
        this.stageManager = stageManager;
        this.build=build;

        eco= new EconomicStageLogic(playerActor);

        setUpBackground();
        setUpElements();
    }



    private void setUpElements() {
        Table table = new Table();

        table.setWidth(this.getWidth());
        table.align(Align.bottom);
        table.setPosition(0, 200);

        Label nameLabel = new Label("Willst du das Hotel kaufen?", skin);
        nameLabel.setFontScale((float) 1.5);
        table.add(nameLabel).width(300).height(100).align(Align.center);
        table.row();

        TextButton buttonTrue = new TextButton("Ja", skin);
        buttonTrue.addListener(buttonClickTrue());
        table.add(buttonTrue).width(100);
        TextButton buttonFalse = new TextButton("Nein", skin);
        buttonFalse.addListener(buttonClickFalse());
        table.add(buttonFalse).width(100);
        table.row();

        Label moneyLabel = new Label("Kosten" + kosten + "", skin);
        moneyLabel.setFontScale((float) 1.5);
        table.add(moneyLabel).width(300).height(100).align(Align.center);
        table.row();

        Label balance = new Label("Guthaben" + eco.getBalance() + "", skin);
        balance.setFontScale((float) 1.5);
        table.add(balance).width(300).height(100).align(Align.center);
        table.row();


        this.addActor(table);


    }


    public void setUpBackground() {

        RectangleActor background = new RectangleActor(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        addActor(background);

    }

    private ClickListener buttonClickTrue() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eco.payMoney(kosten);
                build.setBought(true);
                build.bought();
                stageManager.remove(BuildHotelStage.this);
            }
        };
    }

    private ClickListener buttonClickFalse() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                build.setBought(false);
                stageManager.remove(BuildHotelStage.this);
            }
        };
    }


}