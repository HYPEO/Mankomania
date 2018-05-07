package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;

import space.hypeo.mankomania.StageFactory;

public class LoseMoneyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    Texture texture;
    int price;


    public LoseMoneyFieldActor(float x, float y,Texture texture, int price){
        super(new Texture("lose.jpg"), x, y, FIELD_SCALE, FIELD_SCALE,texture,price);
        this.texture=texture;
        this.price=price;
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance()-100);
        fieldInfo();
    }

    @Override
    public void fieldInfo() {
        StageFactory.setFieldInfoImage(texture);

    }
}