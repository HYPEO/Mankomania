package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by manuelegger on 23.05.18.
 */

public class HorseRaceFieldActor extends FieldActor {
    private StageFactory stageFactory;
    private StageManager stageManager;

    public HorseRaceFieldActor(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, 40f, 40f, price, new Texture("fields/loose_money.png"), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }

    @Override
    public void trigger(PlayerActor player) {
        stageManager.push(stageFactory.getHorseRaceStage(player));
    }
}
