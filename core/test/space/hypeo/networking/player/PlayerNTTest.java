package space.hypeo.networking.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.networking.endpoint.IEndpoint;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PlayerNTTest {

    /* class to test */
    @InjectMocks
    private PlayerNT playerNT;

    @Mock private PlayerManager playerManager;
    @Mock private IEndpoint endpoint;

    @Before
    public void setup() {
        playerManager = mock(PlayerManager.class);
        endpoint = mock(IEndpoint.class);
        playerNT = new PlayerNT(playerManager, endpoint);
    }

    @After
    public void clean_up() {
        playerNT = null;
    }

    @Test
    public void test_getEndpoint() {
        assertThat(playerNT.getEndpoint(), is(endpoint));
    }

    @Test
    public void test_changeBalance() {
        String playerId = "a1C2";
        playerNT.changeBalance(playerId, 0);
        verify(endpoint).changeBalance(playerId, 0);
    }
}
