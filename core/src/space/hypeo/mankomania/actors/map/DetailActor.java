package space.hypeo.mankomania.actors.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import space.hypeo.mankomania.actors.fields.FieldActor;


/**
 * Created by pichlermarc on 11.05.2018.
 */
public class DetailActor extends Group {
    private final Image detailBoard;
    private Image detailImage;
    private float detailImageSize;
    private float detailBoardSize;

    public DetailActor(Texture detailBoardTexture) {
        detailBoardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        detailBoard = new Image(detailBoardTexture);
        this.addActor(detailBoard);
        detailImage = new Image();
        this.addActor(detailImage);

    }

    public void positionActor(float size) {
        if (this.getStage() == null)
            throw new IllegalStateException("DetailActor must be added to a stage before being positioned.");
        detailBoardSize = size;
        detailImageSize = size * 200/320;
        repositionDetailBoard();
        repositionDetailImage();

    }

    private void repositionDetailBoard()
    {
        detailBoard.setBounds(
                (this.getStage().getViewport().getWorldWidth() - detailBoardSize) / 2,
                (this.getStage().getViewport().getWorldHeight() - detailBoardSize) / 2,
                detailBoardSize,
                detailBoardSize);
    }

    private void repositionDetailImage()
    {
        detailImage.setBounds((this.getStage().getViewport().getWorldWidth() - detailImageSize) / 2,
                (this.getStage().getViewport().getWorldHeight() - detailImageSize) / 2,
                detailImageSize,
                detailImageSize);
    }

    public void showDetail(FieldActor field) {
        this.removeActor(detailImage);
        detailImage = new Image(field.getDetailTexture());
        repositionDetailImage();
        this.addActor(detailImage);
    }
}
