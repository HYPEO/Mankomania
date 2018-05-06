package space.hypeo.mankomania.actors;


import com.badlogic.gdx.graphics.Texture;

/**
 * A dummy field used as a placeholder.
 */
public class EmptyFieldActor extends FieldActor{
    private static final float FIELD_SCALE = 30f;

    /**
     * Creates a new instance of the EmptyFieldActor class.
     * @param x Position on the x-Axis.
     * @param y Position on the y-Axis.
     */
    public EmptyFieldActor(float x, float y)
    {
        super(new Texture("wall.jpg"), x, y, FIELD_SCALE, FIELD_SCALE);
    }


    @Override
    public void trigger(PlayerActor player) {
        // Empty trigger method (placeholder)
    }
}
