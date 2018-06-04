package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.player.PlayerManager;

/*
 * There is NO interaction in this stage possible
 */
public class CreatePlayerActorStage extends Stage {
    private final Viewport viewport;
    private PlayerManager playerManager;

    public CreatePlayerActorStage(Viewport viewport, PlayerManager playerManager) {
        this.viewport = viewport;
        this.playerManager = playerManager;

        setupBackground();
        setupLayout();
    }

    private void setupBackground() {
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        this.addActor(background);
    }

    private void setupLayout() {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* very outer table for all widgets */
        Table rootTable = new Table();
        //rootTable.setDebug(true); // turn on all debug lines
        rootTable.setFillParent(true);
        this.addActor(rootTable);

        Label title = new Label("CREATE PLAYER", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);
        title.setAlignment(Align.center);

        /* add title */
        rootTable.add(title).padTop(50).padBottom(50);
        rootTable.row();

        /* inner table contains players from lobby: represented as button */
        Table btnTable = new Table();
        int btnHeight = 100;
        int btnWidth = 250;

        /* data rows */
        for(PlayerActor playerActor : playerManager.getPlayers()) {

            Button btnPlayer = new TextButton(playerActor.getName(), skin);
            btnPlayer.setColor(playerActor.getColor());

            btnTable.add(btnPlayer).height(btnHeight).width(btnWidth);
            btnTable.row();
        }

        /* add buttons */
        rootTable.add(btnTable);

        this.addActor(rootTable);
    }
}
