package space.hypeo.mankomania.actors.symbol;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by manuelegger on 15.06.18.
 */

public class SymbolActor extends Image {
    int symbolID;

    public SymbolActor(int symbolID) {
        super(new Texture("players/player_" + symbolID + ".png"));
        this.symbolID = symbolID;
    }

    public int getSymbolID() {
        return symbolID;
    }
}
