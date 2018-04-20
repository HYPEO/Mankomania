package space.hypeo.mankomania;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

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
        this.spriteBatch = spriteBatch;
        setTexture(texturePath);
    }

    public void draw()
    {
        if(texture !=null)
            spriteBatch.draw(this.texture, this.position.x, this.position.y, this.scale.x, this.scale.y);
    }

    /**
     * Replaces the texture on this GameObject with a new one.
     * @param texture
     */
    public void setTexture(String texture)
    {
        this.texture = new Texture(texture);
        this.scale = new Vector3(this.texture.getWidth(), this.texture.getHeight(), this.texture.getDepth());
    }
}
