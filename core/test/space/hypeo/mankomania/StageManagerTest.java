package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by pichlermarc on 10.05.2018.
 */
public class StageManagerTest extends GameTest {
    private StageManager stageManager;

    @Mock
    private Stage firstStage;
    @Mock
    private Stage secondStage;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        stageManager = new StageManager();
    }

    @Test
    public void push() {
        stageManager.push(firstStage);
        assertEquals(stageManager.getCurrentStage(), firstStage);
    }

    @Test
    public void remove() {
        stageManager.push(firstStage);
        stageManager.push(secondStage);
        assertTrue(stageManager.remove(secondStage));
        assertEquals(stageManager.getCurrentStage(), firstStage);

    }

    public void removeOnEmptyManager() {
        try {
            stageManager.remove(firstStage);
            fail();
        } catch (EmptyStackException ignored) {
        }
    }

    @Test
    public void removeNonTopStage() {
        stageManager.push(firstStage);
        assertFalse(stageManager.remove(secondStage));
        assertEquals(stageManager.getCurrentStage(), firstStage);
    }
}