package space.hypeo.mankomania.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.hypeo.mankomania.player.PlayerBusiness;
import space.hypeo.mankomania.player.PlayerManager;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerBusinessTest {

    /* class to test */
    @InjectMocks
    private PlayerBusiness playerBusiness;

    @Mock
    private PlayerManager playerManager;

    @Before
    public void setup() {
        playerManager = mock(PlayerManager.class);
        playerBusiness = new PlayerBusiness("test_raw", playerManager);
    }

    @After
    public void clean_up() {
        playerBusiness = null;
    }

    @Test
    public void test_getPlayerManager() {
        assertEquals(playerBusiness.getPlayerManager(), playerManager);
    }
}
