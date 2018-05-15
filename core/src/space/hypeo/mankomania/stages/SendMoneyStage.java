package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;

/**
 * Created by pichlermarc on 09.05.2018.
 */
public class SendMoneyStage extends Stage {
    public SendMoneyStage(Viewport viewport, StageManager stageManager) {
        super(viewport);
        //TODO: Get Current NetworkPlayer

        // Set up skin
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up buttons.
        Button send = new TextButton("Send", skin);
        Button button10000 = new TextButton("10.000€", skin);
        Button button30000 = new TextButton("30.000€", skin);
        Button button50000 = new TextButton("50.000€", skin);
        Button button100000 = new TextButton("100.000€", skin);
        SelectBox playersBox = new SelectBox(skin);
        TextField moneyToSend = new TextField("", skin);
        playersBox.setItems("NetworkPlayer 1", "NetworkPlayer 2", "NetworkPlayer 3", "NetworkPlayer 4");
        //TODO: Remove current NetworkPlayer from list, Get Generated NetworkPlayer List from NetworkPlayer Actor
        // Add click listeners.
        send.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //get NetworkPlayer to Send
                String playerToSend;
                Integer amount;
                //playerToSend = (String)playersBox.getSelected();
                //get Money
                //amount = Integer.parseInt(moneyToSend.getText());

                //Method from class which handels logic stuff (PlayerActor etc.)
                stageManager.remove(SendMoneyStage.this);
                stageManager.push(StageFactory.getMapStage(viewport, stageManager));

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
        //TODO: Not high prio smoother design
        Label title = new Label("Send money", skin);
        Label labelAmount = new Label("Amount:", skin);
        Table table = new Table();
        Table moneyButtonTable = new Table();
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

        this.addActor(table);

    }
}
