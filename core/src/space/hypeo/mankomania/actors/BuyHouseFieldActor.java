package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BuyHouseFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    private int x = 0;

    public BuyHouseFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(new Texture("forsale.jpg"), x, y, FIELD_SCALE, FIELD_SCALE, texture, price, fieldInfoImage);
    }

    @Override
    public void trigger(PlayerActor player) {
        if (x == 0) {
            BuyHouseFieldActor.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("bought.jpg"))));
            player.setBalance(player.getBalance() - 100000);
            System.out.println("Bought House");
            showFieldDetail();
            x++;
        } else
            showFieldDetail();
        System.out.println("test");
    }
}
