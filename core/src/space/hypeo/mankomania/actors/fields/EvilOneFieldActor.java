package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class EvilOneFieldActor extends FieldActor {
    private StageFactory stageFactory;
    private StageManager stageManager;

    public EvilOneFieldActor(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, 40f, 40f, price, new Texture("fields/evil-one.png"), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }

    @Override
    public void trigger(PlayerActor player) {
        stageManager.push(stageFactory.getEvilOneStage(player));
    }
}
