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

import space.hypeo.mankomania.GameTest;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;

import static org.junit.Assert.*;

/**
 * Tests the PlayerActor Class.
 */
public class PlayerActorTest extends GameTest {

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

    private PlayerActor playerActor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        playerActor = new PlayerActor(actorImage, BALANCE, true, stageManager, stageFactory);
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

    @Test
    public void moveNonLocalActor() {

        // SET-UP:
        // Set up nonlocal Player
        playerActor = new PlayerActor(actorImage, BALANCE, false, stageManager, stageFactory);
        playerActor.initializeState(fieldActor, playerDetailActor);

        // Return predefined following field on method call.
        FieldActor targetFieldActor = Mockito.mock(FieldActor.class);
        Mockito.when(fieldActor.getFollowingField(4)).thenReturn(targetFieldActor);

        // Reset actorImage, since I only want to verify whether it moved on move() call.
        Mockito.reset(actorImage);

        // EXECUTION:
        // Move player.
        playerActor.move(4);

        // VERIFICATION:
        // Verify that targetFieldActor has not been triggered, since non-local Actors should not trigger fields on device.
        Mockito.verify(targetFieldActor, Mockito.never()).trigger(playerActor);
        // Verify that bounds have been updated, meaning that the player moved somewhere on-screen.
        Mockito.verify(actorImage).setBounds(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }

    @Test
    public void getBalance() {
        // VERIFICATION:
        // Check if balance has been set correctly.
        assertEquals(BALANCE, playerActor.getBalance());
    }

    @Test
    public void setBalance() {
        // EXECUTION:
        playerActor.setBalance(20000);

        // VERIFICATION:
        assertEquals(20000, playerActor.getBalance());
        // Verify that playerDetailActor has been notified to update.s
        Mockito.verify(playerDetailActor).updateBalance(20000);
    }


}