package space.hypeo.mankomania.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * A dummy field used as a placeholder.
 */
public class EmptyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    Texture texture;
    int price;
    Image fieldInfoImage;


    /**
     * Creates a new instance of the EmptyFieldActor class.
     *
     * @param x Position on the x-Axis.
     * @param y Position on the y-Axis.
     */
    public EmptyFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(new Texture("wall.jpg"), x, y, FIELD_SCALE, FIELD_SCALE, texture, price);
        this.texture = texture;
        this.price = price;
        this.fieldInfoImage = fieldInfoImage;
    }


    @Override
    public void trigger(PlayerActor player) {
        // Empty trigger method (placeholder)
        fieldInfo();
    }

    @Override
    public void fieldInfo() {
        fieldInfoImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
    }
}