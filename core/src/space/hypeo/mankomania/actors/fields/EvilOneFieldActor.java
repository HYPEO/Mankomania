package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class EvilOneFieldActor extends FieldActor {
    private StageFactory stageFactory;
    private StageManager stageManager;
    /**
     * Creates instance of EvilOneFieldActor.
     * @param x
     * @param y
     * @param price
     * @param texture
     * @param detailActor
     * @param stageManager
     * @param stageFactory
     */
    public EvilOneFieldActor(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, 40f, 40f, price, new Texture("fields/evil-one.png"), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }
    /**
     * Ovverides the Trigger method to start the correct Stage
     * @param player
     */
    @Override
    public void trigger(PlayerActor player) {
        stageManager.push(stageFactory.getEvilOneStage(player));
    }
}
