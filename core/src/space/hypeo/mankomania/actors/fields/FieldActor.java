package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Represents a Field.
 */
public abstract class FieldActor extends Image {
    private FieldActor nextField;
    private int price;
    private Texture detailTexture;
    protected DetailActor detailActor;

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
    public FieldActor(float x, float y, float width, float height, int price, Texture texture,
                      Texture detailTexture,
                      DetailActor detailActor) {
        super(texture);
        this.setBounds(x, y, width, height);
        this.detailTexture = detailTexture;
        this.price = price;
        this.detailActor = detailActor;

        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                detailActor.showDetail(FieldActor.this);
                return true;
            }
        });
    }

    /**
     * Triggers this field for the player.
     *
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Texture getDetailTexture() {
        return detailTexture;
    }

}