package space.hypeo.mankomania.actors.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by pichlermarc on 26.04.2018.
 */
public class RectangleActor extends Actor {
    private ShapeRenderer rectangleRenderer;

    public RectangleActor(float x, float y, float width, float height) {
        //Set up rectangle.
        rectangleRenderer = new ShapeRenderer();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        rectangleRenderer.begin(ShapeRenderer.ShapeType.Filled);
        rectangleRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        rectangleRenderer.setTransformMatrix(batch.getTransformMatrix());
        rectangleRenderer.translate(getX(), getY(), 0);
        rectangleRenderer.setColor(this.getColor());
        rectangleRenderer.rect(getX(), getY(), getWidth(), getHeight());
        rectangleRenderer.end();

        batch.begin();
    }
}
