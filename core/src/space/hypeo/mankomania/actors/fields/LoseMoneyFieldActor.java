package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

public class LoseMoneyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 40f;
    private static final String TEXTURE_PATH = "fields/loose_money.png";

    public LoseMoneyFieldActor(float x, float y, Texture texture, int price, DetailActor fieldDetail) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, fieldDetail);
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance() - 100);
        detailActor.showDetail(this);
    }
}