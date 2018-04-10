package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import space.hypeo.spriteforce.GameObject;

/**
 * Created by pichlermarc on 06.04.2018.
 */

/**
 * libGDX-specific GameObject implementation.
 */
public class GdxGameObject extends GameObject {
    private SpriteBatch spriteBatch;
    private Texture texture;

    public GdxGameObject(SpriteBatch spriteBatch)
    {
        this.spriteBatch = spriteBatch;
    }

    public GdxGameObject(SpriteBatch spriteBatch, String texturePath)
    {
        setTexture(texturePath);
        this.spriteBatch = spriteBatch;
    }

    public void draw()
    {
        spriteBatch.draw(this.texture, this.position.x, this.position.y);
    }

    /**
     * Replaces the texture on this GameObject with a new one.
     * @param texture
     */
    public void setTexture(String texture)
    {
        this.texture = new Texture(texture);
    }
}
