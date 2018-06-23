
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

public class FinishedHotelStage extends Stage {

    private StageManager stageManager;
    private PlayerActor playerActor;
    private Table table = new Table();
    private Label nameLabel;
    private Label infoLabel;
    private Label moneyLabel;
    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    private TextButton buttonTrue;
    private boolean owner;
    private int money=1000;
    private EconomicStageLogic eco;


    public FinishedHotelStage(Viewport viewport, StageManager stageManager, PlayerActor playerActor,boolean owner) {
        super(viewport);
        this.stageManager = stageManager;
        this.playerActor = playerActor;
        this.owner=owner;

        eco= new EconomicStageLogic(playerActor);

        setUpBackground();
        choose();
    }

    private void choose(){
        if(owner){
            setUpOwner();
            pay(-BuildHotel.getCosts());

        }else{
            pay(money);
            setUpGuest();
        }

    }
    private void setUpOwner(){
        table.setWidth(this.getWidth());
        table.align(Align.bottom);
        table.setPosition(0, 200);

        nameLabel = new Label("Bist jetzt haben deine Mitspieler", skin);
        nameLabel.setFontScale((float) 1.5);
        table.add(nameLabel).width(300).height(100).align(Align.center);
        table.row();

        infoLabel = new Label(BuildHotel.getCosts()+ " bekommen", skin);
        infoLabel.setFontScale((float) 1.5);
        table.add(infoLabel).width(300).height(100).align(Align.center);
        table.row();


        buttonTrue = new TextButton("Zahle sie aus", skin);
        buttonTrue.addListener(buttonClick());
        table.add(buttonTrue).width(100);
        table.row();

        moneyLabel = new Label("your current money is: " + (playerActor.getBalance()-BuildHotel.getCosts()) + "", skin);
        moneyLabel.setFontScale((float) 1.5);
        table.add(moneyLabel).width(300).height(100).align(Align.center);
        table.row();


        this.addActor(table);

    }

    private void setUpGuest() {


        table.setWidth(this.getWidth());
        table.align(Align.bottom);
        table.setPosition(0, 200);

        nameLabel = new Label("Leider ist das Hotel noch nicht fertig", skin);
        nameLabel.setFontScale((float) 1.5);
        table.add(nameLabel).width(300).height(100).align(Align.center);
        table.row();

        infoLabel = new Label("Als Trost bekommst du daher "+money+"", skin);
        infoLabel.setFontScale((float) 1.5);
        table.add(infoLabel).width(300).height(100).align(Align.center);
        table.row();


        buttonTrue = new TextButton("Schade", skin);
        buttonTrue.addListener(buttonClick());
        table.add(buttonTrue).width(100);
        table.row();

        moneyLabel = new Label("your current money is: " + playerActor.getBalance() + "", skin);
        moneyLabel.setFontScale((float) 1.5);
        table.add(moneyLabel).width(300).height(100).align(Align.center);
        table.row();


        this.addActor(table);


    }

    private void pay(int money){
        eco.getMoney(money);
        BuildHotel.setCosts(BuildHotel.getCosts()+money);

    }


    public void setUpBackground() {

        RectangleActor background = new RectangleActor(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        addActor(background);

    }

    private ClickListener buttonClick() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(FinishedHotelStage.this);
            }
        };
    }

}

