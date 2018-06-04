package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.LotteryStage;
import space.hypeo.mankomania.stages.MapStage;

public class LotteryField extends FieldActor {

    private static final float FIELD_SCALE = 40f;
    private static final String TEXTURE_PATH = "lottery_clicked.png";
    boolean first = false;
    LotteryStage lottery;



    /**
     * @param x           X position of the Actor.
     * @param y           Y position of the Actor.
     * @param price       Price of this field.
     * @param texture     Texture that represents the field on screen.
     * @param detailActor The image is shown inside, and replaced by detailTexture.
     */
    public LotteryField (float x, float y, Texture texture, int price, DetailActor detailActor) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, detailActor);

    }

    @Override
    public void trigger(PlayerActor player) {
        if(lottery.isFirst()){
            first= true;
            lottery.setFirst(false);
        }
        if(first){
            lottery.popupGetMoney();
        }else{
            lottery.popupPayMoney();
        }


    }


}