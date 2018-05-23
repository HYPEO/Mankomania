package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.GameTest;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;

import static org.junit.Assert.assertEquals;

/**
 * Created by pichlermarc on 20.05.2018.
 */
public class LocalPlayerActorTest extends GameTest {
    private static final int BALANCE = 100000;

    @Mock
    private Image actorImage;
    @Mock
    private Texture detailTexture;
    @Mock
    private StageManager stageManager;
    @Mock
    private StageFactory stageFactory;
    @Mock
    private FieldActor fieldActor;
    @Mock
    private PlayerDetailActor playerDetailActor;
    @Mock
    private GameStateManager gameStateManager;

    private PlayerActor playerActor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        playerActor = new LocalPlayerActor(actorImage, BALANCE, stageManager, stageFactory, gameStateManager);
        playerActor.initializeState(fieldActor, playerDetailActor);
    }

    @Test
    public void moveLocalActor() {

        // SET-UP:
        Mockito.reset(actorImage);

        // Return predefined following field on method call.
        FieldActor targetFieldActor = Mockito.mock(FieldActor.class);
        Mockito.when(fieldActor.getFollowingField(4)).thenReturn(targetFieldActor);

        // EXECUTION:
        playerActor.move(4);

        // VERIFICATION:
        // Check if targetFieldActor has been triggered.
        Mockito.verify(targetFieldActor).trigger(playerActor);
        // Check if position has been updated.
        Mockito.verify(actorImage).setBounds(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }
}
