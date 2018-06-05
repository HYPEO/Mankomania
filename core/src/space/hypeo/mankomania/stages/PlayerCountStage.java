package space.hypeo.mankomania.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.factories.ActorFactory;
import space.hypeo.mankomania.factories.ButtonFactory;

/**
 * Created by pichlermarc on 31.05.2018.
 */
public class PlayerCountStage extends Stage {
    private StageFactory stageFactory;
    private StageManager stageManager;

    public PlayerCountStage(Viewport viewport, ActorFactory actorFactory, ButtonFactory buttonFactory, StageFactory stageFactory, StageManager stageManager) {
        super(viewport);

        this.stageFactory = stageFactory;
        this.stageManager = stageManager;

        Actor title = actorFactory.getTitle();
        title.setWidth(title.getWidth() / 2.5f);
        title.setHeight(title.getHeight() / 2.5f);
        title.setX((this.getViewport().getWorldWidth() / 2) - (title.getWidth() / 2f) + 10f);
        title.setY((this.getViewport().getWorldHeight() * 3f / 4f) - (title.getHeight() / 2f));

        Actor announcement = actorFactory.getImage("player_count/how_many.png");

        Button twoPlayers = buttonFactory.getButton("player_count_buttons/2_players.png", "player_count_buttons/2_players_clicked.png");
        Button threePlayers = buttonFactory.getButton("player_count_buttons/3_players.png", "player_count_buttons/3_players_clicked.png");
        Button fourPlayers = buttonFactory.getButton("player_count_buttons/4_players.png", "player_count_buttons/4_players_clicked.png");

        twoPlayers.addListener(playerCountClickListener(2));
        threePlayers.addListener(playerCountClickListener(3));
        fourPlayers.addListener(playerCountClickListener(4));

        Table layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.center);
        layout.setPosition(0, this.getHeight() - 450);
        layout.padTop(50);
        layout.add(title).width(400).height(100);
        layout.row();
        layout.add(twoPlayers).width(133).height(133);
        layout.add(threePlayers).width(133).height(133);
        layout.add(fourPlayers).width(133).height(133);
        layout.row();

        this.addActor(layout);
    }

    private ClickListener playerCountClickListener(int playerCount) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(stageManager.getCurrentStage());
                stageManager.push(stageFactory.getMapStage(playerCount));
            }
        };
    }
}
