package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Represents a Field.
 */
public abstract class FieldActor extends Image {
    FieldActor nextField;
    private float x;
    private float y;
    protected int price;
    private Texture info;
    private Image fieldDetailImage;

    /**
     * Creates a new instance of the FieldActor Class.
     *
     * @param texture Texture that represents the field on screen.
     * @param x       X position of the Actor.
     * @param y       Y position of the Actor.
     * @param width   Width of the Actor.
     * @param height  Height of the Actor.
     */
    public FieldActor(Texture texture, float x, float y, float width, float height, Texture info, int price, Image fieldDetailImage) {
        super(texture);
        this.setBounds(x, y, width, height);
        this.x = x;
        this.y = y;
        this.info = info;
        this.price = price;
        this.fieldDetailImage = fieldDetailImage;
    }

    /**
     * Triggers this field for the player.
     *
     * @param player
     */
    public abstract void trigger(PlayerActor player);


    /**
     * Shows the Field Picture in the middle of the map
     */
    protected void showFieldDetail() {
        fieldDetailImage.setDrawable(new SpriteDrawable(new Sprite(info)));
    }

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

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}