package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class ClickerStage extends Stage {


    private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    private Texture einkaufstasche1 = new Texture(Gdx.files.internal("clicker/einkaufstasche1.png"));
    private Texture einkaufstasche2= new Texture(Gdx.files.internal("clicker/einkaufstasche2.png"));
    private Label scoreLabel;
    private int score=0;


    public ClickerStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerActor playerActor) {
        super(viewport);

        setUpBackground();
        scoreLabel = new Label(score+"",skin);
        scoreLabel.setFontScale(1.5f);
        scoreLabel.setX((getWidth()-scoreLabel.getWidth())/2);
        scoreLabel.setY(getHeight()-getHeight()/4);
        this.addActor(scoreLabel);



        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {
                               cheapbag();
                           }
                       }
                , 1f       //    (delay)
                , 1f     //    (seconds)
                ,30

        );
        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {
                               expensivebag();
                           }
                       }
                , 1f       //    (delay)
                , 3f     //    (seconds)
                ,15

        );
        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {
                               stageManager.remove(ClickerStage.this);
                               stageManager.push(stageFactory.clickerStageEndscreen(playerActor,score));

                           }
                       }
                , 37f       //    (delay)
                , 1.5f     //    (seconds)
                ,0

        );



    }
    private void expensivebag(){
        Image expensivebag = new Image(einkaufstasche2);
        float x = MathUtils.random(0, 480-64);
        expensivebag.setX(x);
        expensivebag.setY(800);
        expensivebag.setWidth(64);
        expensivebag.setHeight(64);

        expensivebag.addAction(Actions.moveTo(x,-128,2f));
        expensivebag.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                expensivebag.remove();
                score+=200;
                scoreLabel.setX(getWidth()/2-scoreLabel.getWidth());
                scoreLabel.setText(score+"");
            }
        } );

        this.addActor(expensivebag);
    }

    public void cheapbag(){
        Image cheapbag = new Image(einkaufstasche1);
        float x = MathUtils.random(0, 480-64);
        cheapbag.setX(x);//MathUtils.random(0, 800-64)
        cheapbag.setY(800);
        cheapbag.setWidth(90);
        cheapbag.setHeight(90);

        cheapbag.addAction(Actions.moveTo(x,-128,3.5f));

        cheapbag.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheapbag.remove();
                score+=100;
                scoreLabel.setX(getWidth()/2-scoreLabel.getWidth());
                scoreLabel.setText(score+"");
            }
        } );


        this.addActor(cheapbag);

    }



    public void setUpBackground() {

        RectangleActor background = new RectangleActor(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        addActor(background);

    }




}

