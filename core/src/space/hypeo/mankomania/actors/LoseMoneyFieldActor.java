package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import space.hypeo.mankomania.StageFactory;

public class LoseMoneyFieldActor extends FieldActor {
    private static final float FIELD_SCALE = 30f;
    Texture texture;
    int price;
    Image fieldInfoImage;

    public LoseMoneyFieldActor(float x, float y, Texture texture, int price, Image fieldInfoImage) {
        super(new Texture("lose.jpg"), x, y, FIELD_SCALE, FIELD_SCALE, texture, price);
        this.texture = texture;
        this.price = price;
        this.fieldInfoImage = fieldInfoImage;
    }

    @Override
    public void trigger(PlayerActor player) {
        player.setBalance(player.getBalance() - 100);
        fieldInfo();
    }

    @Override
    public void fieldInfo() {
        fieldInfoImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
    }
}