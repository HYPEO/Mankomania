package space.hypeo.mankomania.actors.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by pichlermarc on 12.05.2018.
 */
public class PlayerDetailActor extends Group {

    private final Image playerDetail;
    private final Label balanceLabel;
    private final Image balanceIcon;

    public PlayerDetailActor(Texture playerTexture) {
        playerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playerDetail = new Image(playerTexture);
        this.addActor(playerDetail);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        balanceLabel = new Label("1000000", skin);
        this.addActor(balanceLabel);

        balanceIcon = new Image(new Texture("common/500_eur_banknote.png"));
        this.addActor(balanceIcon);
    }

    public void positionActor() {
        playerDetail.rotateBy(-30f);
        playerDetail.scaleBy(-0.75f);
        playerDetail.setX(-90f);
        playerDetail.setY(10f);
        balanceLabel.setX(150f);
        balanceLabel.setY(40f);
        balanceIcon.scaleBy(-0.90f);
        balanceIcon.setX(120f);
        balanceIcon.setY(45f);
    }

    public void updateBalance(int balance){
        balanceLabel.setText(Integer.toString(balance));
    }


}
