package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class RouletteFieldActor extends FieldActor{
    private StageFactory stageFactory;
    private StageManager stageManager;
    private Skin skin;
    private Stage parentStage;

    /**
     * @param x             X position of the Actor.
     * @param y             Y position of the Actor.
     * @param width         Width of the Actor
     * @param height        Height of the Actor
     * @param price         Price of this field.
     * @param texture       Texture that represents the field on screen.
     * @param detailTexture Detail texture of this field.
     * @param detailActor   The image is shown inside, and replaced by detailTexture.
     */
    public RouletteFieldActor(float x, float y, float width, float height, int price, Texture texture, Texture detailTexture, DetailActor detailActor, StageFactory stageFactory, StageManager stageManager, Stage parentStage) {
        super(x, y, width, height, price, texture, detailTexture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
        this.parentStage = parentStage;
    }
    public RouletteFieldActor(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory, Stage parentStage) {
        super(x, y, 40f, 40f, price, new Texture("roulettefield.png"), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
        this.parentStage = parentStage;
    }
    @Override
    public void trigger(PlayerActor player) {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        new Dialog("", skin, "dialog") {
            protected void result(Object object) {
                if (object.equals(true)) {
                    stageManager.push(stageFactory.getRouletteStage(player));
                }
            }
        }.text("Do you want to play roulette?").button("Let me play!", true).button("Run away!", false).show(parentStage);
    }
}
