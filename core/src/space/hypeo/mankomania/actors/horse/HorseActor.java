package space.hypeo.mankomania.actors.horse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by manuelegger on 20.05.18.
 */

public class HorseActor extends Image {

    public HorseActor(int horseID) {
        super(new Texture("players/player_" + horseID + ".png"));
    }
}
