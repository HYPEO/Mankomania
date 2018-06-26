package space.hypeo.mankomania.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by pichlermarc on 31.05.2018.
 */
public class ButtonFactory {
    public static Button getButton(String upTexture, String downTexture) {
        Texture hostTextureUp = new Texture(Gdx.files.internal(upTexture));
        hostTextureUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture hostTextureDown = new Texture(Gdx.files.internal(downTexture));
        hostTextureDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion hostTextureRegionUp = new TextureRegion(hostTextureUp);
        TextureRegion hostTextureRegionDown = new TextureRegion(hostTextureDown);

        TextureRegionDrawable hostTextureRegionDrawableUp = new TextureRegionDrawable(hostTextureRegionUp);
        TextureRegionDrawable hostTextureRegionDrawableDown = new TextureRegionDrawable(hostTextureRegionDown);

        return new ImageButton(hostTextureRegionDrawableUp, hostTextureRegionDrawableDown);
    }


    public static Button getPlayerButton(Color color)
    {
        String location = "";
        if(color.equals(Color.CYAN))
            location += "map_assets/player_2.png";
        else if(color.equals(Color.YELLOW))
            location += "map_assets/player_3.png";
        else if(color.equals(Color.PINK))
            location += "map_assets/player_4.png";
        else if(color.equals(Color.GREEN))
            location += "map_assets/player_1.png";
        return getButton(location, location);
    }
}
