package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class FieldActor extends Image{
    FieldActor nextField;

    public FieldActor(Texture texture, float x, float y, float width, float height)
    {
        super(texture);
        this.setBounds(x, y, width, height);
    }

    /**
     * Triggers this field for the player.
     * @param player
     */
    public abstract void trigger(PlayerActor player);

    /**
     * Gets the FieldBehavior of the field n steps from this one.
     *
     * @param steps Amount of steps to look ahead.
     * @return The Field located n steps from this one.
     */
    public FieldActor getFollowingField(int steps) {
        if (steps > 0)
            return this.nextField.getFollowingField(steps - 1);
        else
            return this;
    }

    /**
     * Links up the next field with the specified one.
     *
     * @param nextField
     */
    public void setNextField(FieldActor nextField) {
        this.nextField = nextField;
    }
}
