package space.hypeo.mankomania.actors.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import space.hypeo.mankomania.GameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;

/**
 * Created by pichlermarc on 11.05.2018.
 */
public class RectangleActorTest extends GameTest {

    private RectangleActor rectangleActor;

    @Mock
    private ShapeRenderer shapeRenderer;
    @Mock
    private Batch batch;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static final float X_POS = 10f;
    private static final float Y_POS = 20f;
    private static final float WIDTH = 30f;
    private static final float HEIGHT = 40f;
    private static final float FUZZ_FACTOR = 0.001f;

    @Before
    public void setUp() {
        rectangleActor = new RectangleActor(X_POS, Y_POS, WIDTH, HEIGHT, shapeRenderer);
    }

    @Test
    public void createRectangleActor() {
        assertEquals(X_POS, rectangleActor.getX(), FUZZ_FACTOR);
        assertEquals(Y_POS, rectangleActor.getY(), FUZZ_FACTOR);
        assertEquals(WIDTH, rectangleActor.getWidth(), FUZZ_FACTOR);
        assertEquals(HEIGHT, rectangleActor.getHeight(), FUZZ_FACTOR);
    }

    @Test
    public void createShapeRenderer() {
        try {
            new RectangleActor(X_POS, Y_POS, WIDTH, HEIGHT);
        } catch (IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shapeRendererNullThrowsException() {
        new RectangleActor(X_POS, Y_POS, WIDTH, HEIGHT, null);
    }

    @Test
    public void drawOrder() {
        rectangleActor.draw(batch, 1f);
        InOrder batchOrder = inOrder(batch, shapeRenderer);
        batchOrder.verify(batch).end();

        batchOrder.verify(shapeRenderer).begin(ShapeRenderer.ShapeType.Filled);
        batchOrder.verify(shapeRenderer).end();

        batchOrder.verify(batch).begin();
    }
}