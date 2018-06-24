package space.hypeo.mankomania.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.hypeo.networking.player.PlayerNT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PlayerFactoryTest {

    /* class to test */
    @InjectMocks
    private PlayerFactory playerFactory;

    @Mock
    private PlayerManager playerManager;

    @Before
    public void setup() {
        playerManager = mock(PlayerManager.class);
        playerFactory = new PlayerFactory(playerManager);
    }

    @After
    public void clean_up() {
        playerFactory = null;
    }

    @Test
    public void test_getPlayerManager() {
        assertEquals(playerManager, playerFactory.getPlayerManager());
    }

    @Test
    public void test_getPlayerSkeleton() {
        assertTrue(playerFactory.getPlayerSkeleton("test_123") instanceof PlayerSkeleton);
    }

    @Test
    public void test_getPlayerNT() {
        assertTrue(playerFactory.getPlayerNT() instanceof PlayerNT);
    }

}
