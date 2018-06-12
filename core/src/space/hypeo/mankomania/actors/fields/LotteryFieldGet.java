package space.hypeo.mankomania.actors.fields;


import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.stages.LotteryStage;


public class LotteryField extends FieldActor {
    private StageFactory stageFactory;
    private StageManager stageManager;

    boolean first = false;
    LotteryStage lottery;


    /**
     * @param x           X position of the Actor.
     * @param y           Y position of the Actor.
     * @param price       Price of this field.
     * @param texture     Texture that represents the field on screen.
     * @param detailActor The image is shown inside, and replaced by detailTexture.
     */

    public LotteryField(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, 40f, 40f, price, new Texture("fields/lottery_clicked.png"), texture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }

    @Override
    public void trigger(PlayerActor player) {
        stageManager.push(stageFactory.getLotteryStage(player));
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