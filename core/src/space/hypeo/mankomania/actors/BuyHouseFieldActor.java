package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import space.hypeo.mankomania.StageFactory;

public class BuyHouseFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    Texture fieldInfoImage;
    int x = 0;
    Texture texture;
    int price;

    public BuyHouseFieldActor(float x, float y,Texture texture, int price) {
        super(new Texture("forsale.jpg"), x, y, FIELD_SCALE, FIELD_SCALE,texture,price);
        this.texture=texture;
        this.price=price;
    }

    @Override
    public void trigger(PlayerActor player) {
        if (x == 0) {
            BuyHouseFieldActor.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("bought.jpg"))));
            player.setBalance(player.getBalance() - 100000);
            System.out.println("Bought House");
            fieldInfo();
            x++;
        } else
            fieldInfo();
        System.out.println("test");


    }

    @Override
    public void fieldInfo() {
        StageFactory.setFieldInfoImage(texture);



    }

}
