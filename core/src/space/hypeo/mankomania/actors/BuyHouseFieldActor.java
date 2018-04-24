package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;

public class BuyHouseFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;

    public BuyHouseFieldActor(float x, float y){
        super(new Texture("forsale.jpg"), x, y, FIELD_SCALE, FIELD_SCALE);
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance()-500);
        System.out.println("Bought House");
        FieldActor currentField = new BoughtHouseFieldActor(player.currentField.getX(), player.currentField.getY());



    }

}


