package space.hypeo.mankomania.actors.player;

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
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.sensor.DiceSensorManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;


/**
 * Created by pichlermarc on 20.05.2018.
 */
public class LocalPlayerActorTest extends GameTest {
    private static final int BALANCE = 100000;

    @Mock
    private Image actorImage;
    @Mock
    private FieldActor fieldActor;
    @Mock
    private PlayerDetailActor playerDetailActor;
    @Mock
    private GameStateManager gameStateManager;
    @Mock
    private DiceSensorManager diceSensorManager;

    private PlayerActor playerActor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        playerActor = new LocalPlayerActor(actorImage, BALANCE, diceSensorManager, gameStateManager, playerDetailActor);
        playerActor.initializeState(fieldActor);
    }

    @Test
    public void moveLocalActorNormal() {

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
        // Check if turn has ended.
        Mockito.verify(gameStateManager).endTurn();
    }

    @Test
    public void moveLocalActorNoMoney() {

        // SET-UP:
        playerActor.setBalance(0);
        Mockito.reset(actorImage);

        // Return predefined following field on method call.
        FieldActor targetFieldActor = Mockito.mock(FieldActor.class);
        Mockito.when(fieldActor.getFollowingField(4)).thenReturn(targetFieldActor);

        // EXECUTION:
        playerActor.move(4);

        // VERIFICATION:
        Mockito.verify(gameStateManager).setWinner(playerActor);
    }

    @Test
    public void actNotActive(){
       assertFalse(playerActor.isActive);
       playerActor.setInactive();
       assertFalse(playerActor.isActive);
       playerActor.act(20);
       Mockito.verify(diceSensorManager, never()).trigger(playerActor);
    }

    @Test
    public void actActive(){
        assertFalse(playerActor.isActive);
        playerActor.setActive();
        assertTrue(playerActor.isActive);
        playerActor.act(20);
        Mockito.verify(diceSensorManager, Mockito.times(1)).trigger(playerActor);
    }
}
