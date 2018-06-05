package space.hypeo.mankomania.actors.horse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by manuelegger on 20.05.18.
 */

public class HorseActor extends Image {
    private int id;
    private String horseName;
    private float quote;

    public HorseActor(int horseID, String name, float quote) {
        super(new Texture("players/player_" + horseID + ".png"));
        this.id = horseID;
        this.horseName = name;
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public float getQuote() {
        return quote;
    }

    public void setQuote(float quote) {
        this.quote = quote;
    }
}
