package space.hypeo.mankomania.actors.fields;


import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * A dummy field used as a placeholder.
 */
public class EmptyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 40f;
    private static final String TEXTURE_PATH = "wall.jpg";

    /**
     * Creates a new instance of the EmptyFieldActor class.
     *
     * @param x Position on the x-Axis.
     * @param y Position on the y-Axis.
     */
    public EmptyFieldActor(float x, float y, Texture texture, int price, DetailActor fieldDetail) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, fieldDetail);
    }


    @Override
    public void trigger(PlayerActor player) {
        detailActor.showDetail(this);
    }
}