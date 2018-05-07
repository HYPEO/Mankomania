package space.hypeo.mankomania.actors;


import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;

/**
 * A dummy field used as a placeholder.
 */
public class EmptyFieldActor extends FieldActor{
    private static final float FIELD_SCALE = 30f;
    Texture texture;
    int price;


    /**
     * Creates a new instance of the EmptyFieldActor class.
     * @param x Position on the x-Axis.
     * @param y Position on the y-Axis.
     */
    public EmptyFieldActor(float x, float y,Texture texture, int price)
    {
        super(new Texture("wall.jpg"), x, y, FIELD_SCALE, FIELD_SCALE,texture,price);
        this.texture=texture;
        this.price=price;
    }


    @Override
    public void trigger(PlayerActor player) {
        // Empty trigger method (placeholder)
        fieldInfo();
    }

    @Override
    public void fieldInfo() {
        StageFactory.setFieldInfoImage(texture);



    }
}