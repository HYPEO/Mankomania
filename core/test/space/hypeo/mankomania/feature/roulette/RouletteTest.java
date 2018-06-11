package space.hypeo.mankomania.feature.roulette;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.hypeo.mankomania.Roulette;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.player.PlayerFactory;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.player.PlayerNT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class RouletteTest {
    /* class to test */

    @Mock
    private PlayerActor playerActor;
    private int money;

    @Before
    public void setup() {
        playerActor = mock(PlayerActor.class);
        money = 500;
    }

    @Test
    public void test_getResultGreenWins() {
        Roulette roulette = new Roulette(playerActor,74);
        assertEquals("You Won", roulette.getResult(money,"green"));
    }
    @Test
    public void test_getResultGreenLost() {
        Roulette roulette = new Roulette(playerActor,75);
        assertEquals("You Lost", roulette.getResult(money,"green"));
    }
    @Test
    public void test_getResultBlackWins() {
        Roulette roulette = new Roulette(playerActor,75);
        assertEquals("You Won", roulette.getResult(money,"black"));
    }
    @Test
    public void test_getResultBlackLost() {
        Roulette roulette = new Roulette(playerActor,76);
        assertEquals("You Lost", roulette.getResult(money,"black"));
    }
    @Test
    public void test_getResultRedWins() {
        Roulette roulette = new Roulette(playerActor,76);
        assertEquals("You Won", roulette.getResult(money,"red"));
    }
    @Test
    public void test_getResultRedLost() {
        Roulette roulette = new Roulette(playerActor,77);
        assertEquals("You Lost", roulette.getResult(money,"red"));
    }
    @Test
    public void test_getResultError() {
        Roulette roulette = new Roulette(playerActor,77);
        assertEquals("Error occured", roulette.getResult(money,"Error occured"));
    }


}
