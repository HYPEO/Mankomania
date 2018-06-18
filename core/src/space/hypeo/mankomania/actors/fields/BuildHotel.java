package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;



public class BuildHotel extends FieldActor {

    private static final float FIELD_SCALE = 40f;
    private static final String TEXTURE_PATH = "fields/hotel.png";
    String playerID = "";
    public boolean isBought = false;
    public boolean isReady = false;
    public static int costs;
    private StageFactory stageFactory;
    private StageManager stageManager;


    public BuildHotel(float x, float y, Texture texture, int price, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }



    @Override
    public void trigger(PlayerActor player) {
        Log.info(isBought + "");
        Log.info(isReady + "");
        if (!isBought) {
            stageManager.push(stageFactory.BuildHotelStage(player,this));
            playerID = player.getId();
            Log.info("PlayerID OWner "+playerID);
        } else {
            Log.info("PlayerID Else "+player.getId());
            if (!playerID.equals(player.getId())) {
                stageManager.push(stageFactory.FinishedHotelStage(player, false, playerID));
            } else {
                stageManager.push(stageFactory.FinishedHotelStage(player, true, playerID));
            }

        }


        detailActor.showDetail(this);
    }

    public void bought() {
        BuildHotel.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("fields/hotel_bought.png"))));
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

}

