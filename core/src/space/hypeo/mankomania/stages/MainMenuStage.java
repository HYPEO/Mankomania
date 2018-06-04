package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.player.PlayerFactory;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.IDeviceStatePublisher;
import space.hypeo.networking.network.Role;

/**
 * Holds all widgets on the main menu.
 */
public class MainMenuStage extends Stage {
    private StageManager stageManager;
    private Button launch;
    private Button host;
    private Button join;
    private Button roulette;
    private Image title;
    private Table layout;

    private StageFactory stageFactory;
    private IDeviceStatePublisher deviceStatePublisher;

    private PlayerManager playerManager;

    /**
     * Creates the Main Menu
     *
     * @param stageManager StageManager needed to switch between stages, create new ones, etc.
     * @param viewport     Viewport needed by Stage class.
     */
    public MainMenuStage(StageManager stageManager, Viewport viewport, StageFactory stageFactory, IDeviceStatePublisher deviceStatePublisher) {
        super(viewport);

        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.deviceStatePublisher = deviceStatePublisher;

        setUpBackground();
        createWidgets();
        setupClickListeners();
        setupLayout();

        this.addActor(title);
        this.addActor(layout);
    }

    private void createWidgets() {
        // Set up Title.
        Texture titleTexture = new Texture("common/mankomania_logo_shadowed.png");
        titleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title = new Image(titleTexture);

        launch = createButton("menu_buttons/play_offline.png", "menu_buttons/play_offline_clicked.png");
        join = createButton("menu_buttons/join_game.png", "menu_buttons/join_game_clicked.png");
        host = createButton("menu_buttons/host_game.png", "menu_buttons/host_game_clicked.png");
        roulette = createButton("menu_buttons/host_game.png", "menu_buttons/host_game_clicked.png");
    }

    private Button createButton(String upTexture, String downTexture) {
        Texture hostTextureUp = new Texture(Gdx.files.internal(upTexture));
        hostTextureUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture hostTextureDown = new Texture(Gdx.files.internal(downTexture));
        hostTextureDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion hostTextureRegionUp = new TextureRegion(hostTextureUp);
        TextureRegion hostTextureRegionDown = new TextureRegion(hostTextureDown);

        TextureRegionDrawable hostTextureRegionDrawableUp = new TextureRegionDrawable(hostTextureRegionUp);
        TextureRegionDrawable hostTextureRegionDrawableDown = new TextureRegionDrawable(hostTextureRegionDown);

        return new ImageButton(hostTextureRegionDrawableUp, hostTextureRegionDrawableDown);
    }
    private ClickListener rouletteClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayerActor pa;
                pa = null;
                stageManager.push(stageFactory.getRouletteStage(pa));
            }
        };
    }
    private ClickListener launchClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.push(stageFactory.getMapStage());
            }
        };
    }

    private ClickListener hostClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playerManager = new PlayerManager(stageManager, Role.HOST);
                PlayerFactory playerFactory = new PlayerFactory(playerManager);
                playerManager.setPlayerBusiness(playerFactory.getPlayerBusiness("the_mighty_host"));
                playerManager.setPlayerNT(playerFactory.getPlayerNT());

                deviceStatePublisher.subscribe(playerManager.getPlayerNT());
                stageManager.push(stageFactory.getLobbyStage(playerManager));
            }
        };
    }

    private ClickListener clientClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // TODO: BUG client is in lobby multiple times if returned to main menu
                playerManager = new PlayerManager(stageManager, Role.CLIENT);
                PlayerFactory playerFactory = new PlayerFactory(playerManager);
                playerManager.setPlayerBusiness(playerFactory.getPlayerBusiness("another_client"));
                playerManager.setPlayerNT(playerFactory.getPlayerNT());

                deviceStatePublisher.subscribe(playerManager.getPlayerNT());
                stageManager.push(stageFactory.getDiscoveredHostsStage(playerManager));
            }
        };
    }

    private void setupClickListeners() {
        launch.addListener(this.launchClickListener());
        host.addListener(this.hostClickListener());
        join.addListener(clientClickListener());
        roulette.addListener(this.rouletteClickListener());
    }

    private void setupLayout() {
        // Set up title.
        title.setWidth(title.getWidth() / 2.5f);
        title.setHeight(title.getHeight() / 2.5f);
        title.setX((this.getViewport().getWorldWidth() / 2) - (title.getWidth() / 2f) + 10f);
        title.setY((this.getViewport().getWorldHeight() * 3f / 4f) - (title.getHeight() / 2f));

        layout = new Table();
        layout.setWidth(this.getWidth());
        layout.align(Align.center);
        layout.setPosition(0, this.getHeight() - 450);
        layout.padTop(50);
        layout.add(title).width(400).height(100);
        layout.row();
        layout.add(launch).width(400).height(125);
        layout.row();
        layout.add(host).width(400).height(125);
        layout.row();
        layout.add(join).width(400).height(125);
        layout.row();
        layout.add(roulette).width(400).height(125);
        layout.row();
    }

    private void setUpBackground() {
        RectangleActor background = new RectangleActor(0, 0, this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        this.addActor(background);
    }
}
