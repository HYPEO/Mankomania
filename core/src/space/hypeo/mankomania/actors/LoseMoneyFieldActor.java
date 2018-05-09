package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LoseMoneyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    private static final String TEXTURE_PATH = "lose.jpg";

    public LoseMoneyFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(new Texture(TEXTURE_PATH), x, y, FIELD_SCALE, FIELD_SCALE, texture, price, fieldInfoImage);
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance() - 100);
        showFieldDetail();
    }
}