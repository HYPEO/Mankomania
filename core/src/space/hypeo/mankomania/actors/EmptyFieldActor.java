package space.hypeo.mankomania.behaviour;


import com.badlogic.gdx.graphics.Texture;

public class EmptyFieldActor extends FieldActor{
    private static final float FIELD_SCALE = 30f;

    public EmptyFieldActor(float x, float y)
    {
        super(new Texture("wall.jpg"), x, y, FIELD_SCALE, FIELD_SCALE);
    }

    @Override
    public void trigger(PlayerActor player) {
        // Empty trigger method (placeholder)
    }
}
