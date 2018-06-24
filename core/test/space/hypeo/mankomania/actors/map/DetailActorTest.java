package space.hypeo.mankomania.actors.map;

import com.badlogic.gdx.graphics.Texture;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by pichlermarc on 11.05.2018.
 */
public class DetailActorTest {

    DetailActor detailActor;

    @Mock
    private Texture texture;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        detailActor = new DetailActor(texture);
    }

    @Test(expected = IllegalStateException.class)
    public void positionActorFailsWithoutViewport(){
        detailActor.positionActor(320);
    }
}