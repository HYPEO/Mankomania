package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class BuyHotelFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    private static final String TEXTURE_PATH = "forsale.jpg";
    private boolean bought = false;

    public BuyHotelFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, fieldInfoImage);
    }

    @Override
    public void trigger(PlayerActor player) {
        if (!bought) {
            BuyHotelFieldActor.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("bought.jpg"))));
            player.setBalance(player.getBalance() - 100000);
            System.out.println("Bought House");
            bought = true;
        }
        showFieldDetail();
    }
}
