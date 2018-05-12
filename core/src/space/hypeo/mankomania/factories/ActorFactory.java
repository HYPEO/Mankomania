package space.hypeo.mankomania.factories;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.actors.map.DetailActor;

/**
 * Created by pichlermarc on 11.05.2018.
 */
public class ActorFactory {
    private ActorFactory() {
    }

    /*
    Provides a new DetailActor
     */
    public static DetailActor getDetailActor() {
        return new DetailActor(new Texture("map_assets/detail_board.png"));
    }
}
