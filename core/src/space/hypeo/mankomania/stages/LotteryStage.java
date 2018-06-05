package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class LotteryStage extends Stage {

    boolean first=true;
    int moneypool=0;
    int money = 1000;
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;
    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    public LotteryStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;


    }

    public void popupGetMoney() {
        if (money == 0) {
            popupPayMoney();
        } else {
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            new Dialog("", skin, "dialog") {
                protected void result(Object object) {
                    System.out.println("Chosen: " + object);
                    playerActor.setBalance(playerActor.getBalance() + moneypool);
                    moneypool=0;

                }
            }.text("You get " + moneypool + " €").button("OK", true).key(Input.Keys.ENTER, true)
                    .key(Input.Keys.ESCAPE, false).show(stageManager.getCurrentStage());
        }
    }


    public void popupPayMoney() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        new Dialog("", skin, "dialog") {
            protected void result(Object object) {
                System.out.println("Chosen: " + object);
                playerActor.setBalance(playerActor.getBalance() - moneypool);
                moneypool+=money;


            }
        }.text("You loose " + money + " €").button("OK", true).key(Input.Keys.ENTER, true)
                .key(Input.Keys.ESCAPE, false).show(stageManager.getCurrentStage());
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
}