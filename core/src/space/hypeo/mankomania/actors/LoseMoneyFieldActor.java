package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;

public class LoseMoneyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;

    public LoseMoneyFieldActor(float x, float y){
        super(new Texture("lose.jpg"), x, y, FIELD_SCALE, FIELD_SCALE);
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance()-100);
    }
}
