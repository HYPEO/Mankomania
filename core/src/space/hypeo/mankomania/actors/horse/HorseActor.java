package space.hypeo.mankomania.actors.horse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by manuelegger on 20.05.18.
 */

public class HorseActor extends Image implements Comparable<HorseActor> {
    private int id;
    private String horseName;
    private float quote;
    private float raceTime;

    public HorseActor(int horseID, String name, float quote) {
        super(new Texture("players/player_" + horseID + ".png"));
        this.id = horseID;
        this.horseName = name;
        this.quote = quote;
    }

    public HorseActor(int horseID, String name, float quote, Texture texture) {
        super(texture);
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

    public float getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }

    @Override
    public int compareTo(HorseActor horseActor) {
        if(raceTime > horseActor.getRaceTime()) {
            return 1;
        } else if (raceTime < horseActor.getRaceTime()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
