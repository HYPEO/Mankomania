package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Collection;

import space.hypeo.mankomania.DigitFilter;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class SendMoneyStage extends Stage {
    private Table table;
    private Table moneyButtonTable;
    private StageManager stageManager;
    private Button send;
    private Button button10000;
    private Button button30000;
    private Button button50000;
    private Button button100000;
    private Skin skin;
    private SelectBox playersBox;
    private TextField moneyToSend;
    private Label title;
    private Label labelAmount;
    private Lobby lobby;
    private Collection<PlayerSkeleton> players;

    public SendMoneyStage(Viewport viewport, StageManager stageManager, StageFactory stageFactory, PlayerManager playerManager) {
        super(viewport);
        //TODO: Get Current PlayerNT

        // Set up skin
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.stageManager = stageManager;
        lobby = playerManager.getLobby();
        players = lobby.values();

        setUpControlsAndInputs();
        setUpTable();
        setUpListeners();

        playersBox.setItems("PlayerNT 1", "PlayerNT 2", "PlayerNT 3", "PlayerNT 4");
        //TODO: Remove current PlayerNT from list, Get Generated PlayerNT List from PlayerNT Actor
        //TODO: Not high prio smoother design

        this.addActor(table);

    }
    private void setUpTable(){
        table.setWidth(this.getWidth());
        table.align(Align.center);
        table.setPosition(0, this.getHeight() - 200);
        table.padTop(50);
        table.add(title).width(300).height(100).align(Align.center);
        table.row();
        table.add(playersBox).width(300).height(50);
        table.row().pad(20);
        table.add(labelAmount).width(300).height(50).padBottom(-10);
        table.row();
        table.add(moneyToSend).width(300).height(50);
        table.row();
        table.add(moneyButtonTable);
        moneyButtonTable.pad(20);
        moneyButtonTable.add(button10000).width(147).height(40).padRight(6);
        moneyButtonTable.add(button30000).width(147).height(40);
        moneyButtonTable.row().padBottom(10).padTop(10);
        moneyButtonTable.add(button50000).width(147).height(40).padRight(6);
        moneyButtonTable.add(button100000).width(147).height(40);
        table.row();
        table.row();
        table.add(send).width(300).height(50);
    }
    private void setUpControlsAndInputs()
    {
        title = new Label("Send money", skin);
        labelAmount = new Label("Amount:", skin);
        send = new TextButton("Send", skin);
        button10000 = new TextButton("10.000€", skin);
        button30000 = new TextButton("30.000€", skin);
        button50000 = new TextButton("50.000€", skin);
        button100000 = new TextButton("100.000€", skin);
        SelectBox playersBox = new SelectBox(skin);
        TextField moneyToSend = new TextField("", skin);
        moneyToSend.setTextFieldFilter(new DigitFilter());
    }
    private void setUpListeners()
    {
        send.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //get PlayerNT to Send
                String playerToSend;
                Integer amount;
                //playerToSend = (String)playersBox.getSelected();
                //get Money
                //amount = Integer.parseInt(moneyToSend.getText());

                //Method from class which handels logic stuff (PlayerActor etc.)
                stageManager.remove(SendMoneyStage.this);
            }
        });
        button10000.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moneyToSend.setText("10000");

            }
        });
        button30000.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moneyToSend.setText("30000");

            }
        });
        button50000.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moneyToSend.setText("50000");

            }
        });
        button100000.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moneyToSend.setText("100000");

            }
        });
    }
}
