package space.hypeo.mankomania.sensor;

import com.badlogic.gdx.Gdx;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by pichlermarc on 31.05.2018.
 */
public class DiceSensorManager {
    private Random die = new Random();
    private static final float EARTH_GRAVITY = 9.81f;
    private static final float GRAVITY_FORCE_THRESHOLD = 1.2f;
    private StageManager stageManager;
    private StageFactory stageFactory;


    public DiceSensorManager(StageManager stageManager, StageFactory stageFactory) {
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
    }

    public void trigger(PlayerActor playerActor) {
        float xValue;
        float yValue;
        float zValue;
        float gForce;


        xValue = Gdx.input.getAccelerometerX() / EARTH_GRAVITY;
        yValue = Gdx.input.getAccelerometerY() / EARTH_GRAVITY;
        zValue = Gdx.input.getAccelerometerZ() / EARTH_GRAVITY;

        gForce = (float) Math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue);

        if (gForce > GRAVITY_FORCE_THRESHOLD) {
            int moveFields = die.nextInt(6) + 1;
            playerActor.move(moveFields);
            stageManager.push(stageFactory.getDiceResultStage(moveFields));
        }
    }

}
