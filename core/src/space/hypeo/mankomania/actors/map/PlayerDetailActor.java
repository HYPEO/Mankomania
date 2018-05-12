package space.hypeo.mankomania.actors.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by pichlermarc on 12.05.2018.
 */
public class PlayerDetailActor extends Group {

    private final Image playerDetail;

    public PlayerDetailActor(Texture playerTexture)
    {
        playerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playerDetail = new Image(playerTexture);
        this.addActor(playerDetail);
    }

    public void positionActor()
    {
        playerDetail.rotateBy(-30f);
        playerDetail.scaleBy(-0.75f);
        playerDetail.setX(-90);
        playerDetail.setY(10);
    }
}
