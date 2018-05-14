package space.hypeo.mankomania.actors.fields;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import space.hypeo.mankomania.actors.player.PlayerActor;


public class BuildHotel extends FieldActor {

        private static final float FIELD_SCALE = 30f;
        private static final String TEXTURE_PATH = "buildhotel.jpg";
        Skin skin;
        Stage stage;
        boolean bought=false;
        String playerID="";

        public BuildHotel(float x, float y, Texture texture, int price, Image fieldInfoImage, Stage stage) {
            super(x, y, FIELD_SCALE, FIELD_SCALE, price, new Texture(TEXTURE_PATH), texture, stage, fieldInfoImage);
            this.stage=stage;
        }

        @Override
        public void trigger(PlayerActor player) {
            if(!bought) {
                skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
                Gdx.input.setInputProcessor(stage);
                new Dialog("", skin, "dialog") {
                    protected void result(Object object) {
                        System.out.println("Chosen: " + object);
                        if(object.equals(true)) {
                            bought = true;
                            BuildHotel.this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tile.png"))));
                        }
                    }
                }.text("Do you want to build a hotel?").button("Yes", true).button("No", false).key(Input.Keys.ENTER, true)
                        .key(Input.Keys.ESCAPE, false).show(stage);
            }else{
                // TODO: SpielerID müssen noch eingebaut werden
                if(!playerID.equals("")){
                    player.setBalance(player.getBalance()+500);
                }else{
                    System.out.println("you bought this");
                }

            }

        }
    }


