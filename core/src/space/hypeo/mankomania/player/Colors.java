package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the colors, that are available for a player.
 */
public class Colors {

    private Colors() {}

    private static final Set<Color> avaiableColors =
            new HashSet<>(Arrays.asList(
                    new Color(Color.BLACK),
                    new Color(Color.BLUE),
                    new Color(Color.BROWN),
                    new Color(Color.CYAN),
                    new Color(Color.GOLD),
                    new Color(Color.GREEN),
                    new Color(Color.MAGENTA),
                    new Color(Color.ORANGE),
                    new Color(Color.PINK),
                    new Color(Color.PURPLE),
                    new Color(Color.RED),
                    new Color(Color.WHITE),
                    new Color(Color.YELLOW)
            ));

    public static final Set<Color> getAvailableColors() {
        return avaiableColors;
    }
}
