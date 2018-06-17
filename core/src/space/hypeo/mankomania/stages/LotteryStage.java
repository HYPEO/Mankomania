package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;


import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class LotteryStage extends Stage {
    int moneypool;
    int money = 1000;
    private StageManager stageManager;
    private StageFactory stageFactory;
    private PlayerActor playerActor;
    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    private TextButton buttonTrue;
    private Table table = new Table();
    private Label nameLabel;
    private Label moneyLabel;
    private Boolean pay;
    private String textTrue = "Sorry, you lost: " + money + "";
    private String textFalse = "Congratulations! You won: " + moneypool + "";
    Preferences prefs = Gdx.app.getPreferences("Lottery");

    private final float targetFPS = 100f;
    private final long targetDelay = 1000 / (long) targetFPS;
    private long diff, start;

    public LotteryStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor, Boolean pay) {
        super(viewport);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.playerActor = playerActor;
        this.pay = pay;

        Log.info(moneypool+"");
        setUpBackground();

        moneypool=prefs.getInteger("moneypool",0);

        if (!pay && moneypool > 0) {
            setUpElements(textFalse);
        } else {
            setUpElements(textTrue);

        }


    }

    private ClickListener buttonClick() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!pay && moneypool > 0) {
                    Log.info("pay");
                    // animation(moneypool);
                    playerActor.setBalance(playerActor.getBalance() + moneypool);
                    moneyLabel.setText("your current money is: " + playerActor.getBalance() + "");
                    prefs.putInteger("moneypool", 0);

                } else {
                    Log.info("else");
                    //animation(money);
                    playerActor.setBalance(playerActor.getBalance() - money);
                    moneyLabel.setText("your current money is: " + playerActor.getBalance() + "");
                    prefs.putInteger("moneypool", moneypool += money);
                    Log.info(money+" "+moneypool);

                }
                prefs.flush();

                stageManager.remove(LotteryStage.this);
            }
        };
    }

    private void animation(int x) {
        for (int i = 0; i < x/30; i++) {
            // TODO: Animation
            moneyLabel.setText("your current money is: " + (playerActor.getBalance() - i*30) + "");
            limitFPS();
        }
    }

    private void limitFPS() {
        diff = System.currentTimeMillis() - start;

        if (diff < targetDelay) {
            try {
                Thread.sleep(targetDelay - diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        start = System.currentTimeMillis();
    }


    private void setUpElements(String text) {


        table.setWidth(this.getWidth());
        table.align(Align.bottom);
        table.setPosition(0, 200);

        nameLabel = new Label(text, skin);
        nameLabel.setFontScale((float) 1.5);
        table.add(nameLabel).width(300).height(100).align(Align.center);
        table.row();

        buttonTrue = new TextButton("Okay", skin);
        buttonTrue.addListener(buttonClick());
        table.add(buttonTrue).width(100);
        table.row();

        moneyLabel = new Label("your current money is: " + playerActor.getBalance() + "", skin);
        moneyLabel.setFontScale((float) 1.5);
        table.add(moneyLabel).width(300).height(100).align(Align.center);
        table.row();


        this.addActor(table);


    }


    public void setUpBackground() {

        RectangleActor background = new RectangleActor(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        addActor(background);

    }
}