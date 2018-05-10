package space.hypeo.mankomania;

import com.badlogic.gdx.scenes.scene2d.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Created by pichlermarc on 10.05.2018.
 */
public class StageManagerTest extends GameTest {
    StageManager stageManager;

    @Mock
    Stage firstStage;
    @Mock
    Stage secondStage;

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
        } catch (EmptyStackException exception) {
        }
    }

    @Test
    public void removeNonTopStage() {
        stageManager.push(firstStage);
        assertFalse(stageManager.remove(secondStage));
        assertEquals(stageManager.getCurrentStage(), firstStage);
    }
}