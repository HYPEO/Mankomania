package space.hypeo.mankomania.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.actors.player.LocalPlayerActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.sensor.DiceSensorManager;

/**
 * Created by pichlermarc on 11.05.2018.
 */
public class ActorFactory {
    private static final int INITIAL_BALANCE = 1000000;
    private StageManager stageManager;

    public ActorFactory(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    /*
    Provides a new DetailActor
     */
    public DetailActor getDetailActor() {
        return new DetailActor(new Texture("map_assets/detail_board.png"));
    }

    public PlayerActor getPlayerActor(String id, String nickname, Color playerColor, Boolean isLocal, GameStateManager gameStateManager, StageFactory stageFactory) {
        String texturePath = "";
        String detailTexturePath = "";
        PlayerDetailActor.ScreenPosition position = PlayerDetailActor.ScreenPosition.BOTTOM_LEFT;

        if (playerColor.equals(Color.GREEN)) {
            texturePath = "players/player_1.png";
            detailTexturePath = "map_assets/player_1.png";
            position = PlayerDetailActor.ScreenPosition.BOTTOM_LEFT;
        }
        else if (playerColor.equals(Color.CYAN)) {
            texturePath = "players/player_2.png";
            detailTexturePath = "map_assets/player_2.png";
            position = PlayerDetailActor.ScreenPosition.BOTTOM_RIGHT;
        }
        else if (playerColor.equals(Color.YELLOW)) {
            texturePath = "players/player_3.png";
            detailTexturePath = "map_assets/player_3.png";
            position = PlayerDetailActor.ScreenPosition.TOP_RIGHT;
        }
        else if (playerColor.equals(Color.PINK)){
            texturePath = "players/player_4.png";
            detailTexturePath = "map_assets/player_4.png";
            position = PlayerDetailActor.ScreenPosition.TOP_LEFT;
        }

        PlayerDetailActor detailActor = new PlayerDetailActor(new Texture(detailTexturePath));
        detailActor.positionActor(position);

        DiceSensorManager sensorManager = new DiceSensorManager(stageManager, stageFactory);

        if (isLocal)
            return new LocalPlayerActor(new Image(new Texture(texturePath)),
                    INITIAL_BALANCE,
                    sensorManager,
                    gameStateManager,
                    detailActor);
        else
            return new PlayerActor(new Image(new Texture(texturePath)), INITIAL_BALANCE, detailActor);
    }

    public Image getImage(String path)
    {
        Texture imageTexture = new Texture(path);
        imageTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return new Image(imageTexture);
    }

    public Image getTitle()
    {
        return getImage("common/mankomania_logo_shadowed.png");
    }
}
