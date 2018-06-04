package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;


public class BuildHotel extends FieldActor {

    private static final float FIELD_SCALE = 40f;
    private static final String TEXTURE_PATH = "fields/hotel.png";
    Skin skin;
    Stage stage;
    boolean bought = false;
    boolean isbuilding=true;
    String playerID = "";
    int costs=500;
    long startTime;

    public BuildHotel(float x, float y, Texture texture, int price, DetailActor detailActor, Stage stage) {
        super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, detailActor);
        this.stage = stage;
    }

    @Override
    public void trigger(PlayerActor player) {

        if (!bought) {
            popup();


        } else {
            // TODO: SpielerID müssen noch eingebaut werden

            if (!playerID.equals("")) {
                guest(player);
            } else {
                owner(player);
            }

        }
        detailActor.showDetail(this);
    }


    public void popup() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        new Dialog("", skin, "dialog") {
            protected void result(Object object) {
                System.out.println("Chosen: " + object);

                if (object.equals(true)) {
                    bought = true;
                    startTime = System.currentTimeMillis();
                    BuildHotel.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("fields/building.png"))));
                    building();
                }
            }
        }.text("Do you want to build a hotel?").button("Yes", true).button("No", false).key(Input.Keys.ENTER, true)
                .key(Input.Keys.ESCAPE, false).show(stage);
    }

    public void finish_building(PlayerActor player){
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        new Dialog("", skin, "dialog") {
            protected void result(Object object) {
                System.out.println("Chosen: " + object);

                if (object.equals(true)) {
                    isbuilding = false;
                    BuildHotel.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("fields/hotel_bought.png"))));
                    player.setBalance(player.getBalance()+1000);

                }
            }
        }.text("Do you want to finish the building now?").button("Yes", true).button("No", false).key(Input.Keys.ENTER, true)
                .key(Input.Keys.ESCAPE, false).show(stage);
    }

    public void owner(PlayerActor player) {
        if(isbuilding){
            finish_building(player);
        }else{
            // TODO: Upgrade building ?
        }

    }

    public void guest(PlayerActor player) {
        if(!isbuilding)
            // TODO: Transfer Money from Owner to Guest
            player.setBalance(player.getBalance() + costs);
    }

    public void building(){
        long elapsed_time = System.currentTimeMillis() - startTime;
        if((elapsed_time/1000)>300){
            isbuilding=false;
            BuildHotel.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("fields/hotel_bought.png"))));

        }

    }
}




