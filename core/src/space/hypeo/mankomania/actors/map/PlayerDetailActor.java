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

    public enum ScreenPosition {
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        CENTERED
    }

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


    /**
     * Aligns actor to specified corner
     *
     * @param position The Corner to move the actor to.
     */
    public void positionActor(ScreenPosition position) {
        // How not to position actors on screen...
        playerDetail.setBounds(0, 0, playerDetail.getWidth() * 0.25f, playerDetail.getHeight() * 0.25f);
        playerDetail.setOrigin(playerDetail.getWidth() / 2f, playerDetail.getHeight() / 2f);
        if (position == ScreenPosition.BOTTOM_LEFT) {
            playerDetail.rotateBy(-30f);
            playerDetail.setX(-50f);
            playerDetail.setY(-50f);
            balanceIcon.setBounds(120f, 45f, balanceIcon.getWidth() * 0.1f, balanceIcon.getHeight() * 0.1f);
            balanceLabel.setX(balanceIcon.getX() + 30f);
            balanceLabel.setY(balanceIcon.getY() - 5f);
        } else if (position == ScreenPosition.BOTTOM_RIGHT) {
            playerDetail.rotateBy(32f);
            playerDetail.setX(480 - playerDetail.getWidth() + 47f);
            playerDetail.setY(-57f);
            balanceIcon.setBounds(480 - 120f - balanceIcon.getWidth() * 0.1f, 45f, balanceIcon.getWidth() * 0.1f, balanceIcon.getHeight() * 0.1f);
            balanceLabel.setX(balanceIcon.getX() + 30f - 100f);
            balanceLabel.setY(balanceIcon.getY() - 5f);
        } else if (position == ScreenPosition.TOP_LEFT) {
            playerDetail.rotateBy(212f);
            playerDetail.setX(-47);
            playerDetail.setY(800 - playerDetail.getHeight() + 57f);
            balanceIcon.setBounds(120f, 800 - 45f - balanceIcon.getHeight() * 0.1f, balanceIcon.getWidth() * 0.1f, balanceIcon.getHeight() * 0.1f);
            balanceLabel.setX(balanceIcon.getX() + 30f);
            balanceLabel.setY(balanceIcon.getY() - 5f);
        } else if (position == ScreenPosition.TOP_RIGHT) {
            playerDetail.rotateBy(150f);
            playerDetail.setX(480 - playerDetail.getWidth() + 56f);
            playerDetail.setY(800 - playerDetail.getHeight() + 50f);
            balanceIcon.setBounds(480 - 120f - balanceIcon.getWidth() * 0.1f, 800 - 45f - balanceIcon.getHeight() * 0.1f, balanceIcon.getWidth() * 0.1f, balanceIcon.getHeight() * 0.1f);
            balanceLabel.setX(balanceIcon.getX() + 30f - 100f);
            balanceLabel.setY(balanceIcon.getY() - 5f);
        } else if (position == ScreenPosition.CENTERED) {
            playerDetail.setRotation(0f);
            playerDetail.setBounds(0, 0, playerDetail.getWidth() * 4, playerDetail.getHeight() * 4);
            playerDetail.setX((480 - playerDetail.getWidth())/2f);
            playerDetail.setY((800 - playerDetail.getHeight())/2f);
            this.removeActor(balanceLabel);
            this.removeActor(balanceIcon);
        }
    }

    public void updateBalance(int balance) {
        balanceLabel.setText(Integer.toString(balance));
    }
}
