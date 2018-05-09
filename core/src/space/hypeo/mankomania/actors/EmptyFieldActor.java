package space.hypeo.mankomania.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A dummy field used as a placeholder.
 */
public class EmptyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    private int price;

    /**
     * Creates a new instance of the EmptyFieldActor class.
     *
     * @param x Position on the x-Axis.
     * @param y Position on the y-Axis.
     */
    public EmptyFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(new Texture("wall.jpg"), x, y, FIELD_SCALE, FIELD_SCALE, texture, price, fieldInfoImage);
        this.price = price;
    }


    @Override
    public void trigger(PlayerActor player) {
        // Empty trigger method (placeholder)
        showFieldDetail();
    }
}