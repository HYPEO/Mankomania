package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the colors, that are available for a player.
 */
public class Colors {

    /**
     * No instance of this class allowed.
     */
    private Colors() {}

    /**
     * Allowed colors.
     */
    private static final Set<Color> avaiableColors =
            new HashSet<>(Arrays.asList(
                    new Color(Color.CYAN),
                    new Color(Color.GREEN),
                    new Color(Color.PINK),
                    new Color(Color.YELLOW)
            ));

    /**
     * Gets available = defined colors.
     * @return
     */
    public static final Set<Color> getAvailableColors() {
        return avaiableColors;
    }
}
