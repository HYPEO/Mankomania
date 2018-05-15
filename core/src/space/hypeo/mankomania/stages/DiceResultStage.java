package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class DiceResultStage extends Stage {
    public DiceResultStage(Viewport viewport, final StageManager stageManager, int moveFields) {
        super(viewport);

        // Set up skin
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Texture diceResult = new Texture("dice/dice" + moveFields + ".png");
        Drawable dice = new TextureRegionDrawable(new TextureRegion(diceResult));

        // Set up button
        ImageButton diceButton = new ImageButton(dice);

        Label title = new Label("  You rolled a " + moveFields + " - tap dice to move", skin);

        // Add click listeners.
        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(DiceResultStage.this);
            }
        });

        Table table = new Table();
        table.setWidth(this.getWidth());
        table.align(Align.center);
        table.setPosition(0, this.getHeight() - this.getHeight() / 2);
        table.add(title).width(300).height(100);
        table.row();
        table.add(diceButton).width(350).height(350);

        // Add dice-button to stage.
        this.addActor(table);

    }
}
