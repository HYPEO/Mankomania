package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.Mankomania;

public class BoughtHouseFieldActor extends FieldActor {

    private static final float FIELD_SCALE = 30f;


    public BoughtHouseFieldActor(float x, float y){
        super(new Texture("bought.jpg"), x, y, FIELD_SCALE, FIELD_SCALE);
        this.setBounds(x,y,FIELD_SCALE,FIELD_SCALE);

    }

    @Override
    public void trigger(PlayerActor player) {
    }
}
