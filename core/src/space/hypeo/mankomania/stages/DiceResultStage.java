package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;

public class DiceResultStage extends Stage {
    final StageManager stageManager;

    ImageButton diceButton;
    Label title;
    Drawable dice;
    Texture diceResult;
    Skin skin;

    public DiceResultStage(Viewport viewport, final StageManager stageManager, int moveFields) {
        super(viewport);
        this.stageManager = stageManager;

        diceResult = new Texture("dice/dice" + moveFields + ".png");
        dice = new TextureRegionDrawable(new TextureRegion(diceResult));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        setUpBackground();
        createWidgets(moveFields);
        setUpClickListeners();
        setUpLayout();
    }

    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }

    private void createWidgets(int moveFields) {
        diceButton = new ImageButton(dice);
        title = new Label("  You rolled a " + moveFields + " - tap dice to move", skin);
    }

    private void setUpClickListeners() {
        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(DiceResultStage.this);
            }
        });
    }

    private void setUpLayout() {
        Table table = new Table();
        table.setWidth(this.getWidth());
        table.align(Align.center);
        table.setPosition(0, this.getHeight() - this.getHeight() / 2);
        table.add(title).width(300).height(100);
        table.row();
        table.add(diceButton).width(350).height(350);

        this.addActor(table);
    }
}
