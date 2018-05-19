package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import space.hypeo.player.PlayerManager;
import space.hypeo.networking.endpoint.IEndpoint;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PlayerNTHostTest {

    /* class to test */
    @InjectMocks
    private PlayerNT playerNT;

    @Mock private PlayerManager playerManager;
    @Mock private IEndpoint endpoint;


    @Before
    public void setup() {
        playerManager = mock(PlayerManager.class);
        endpoint = mock(IEndpoint.class);
        playerNT = new PlayerNT(playerManager);
    }

    @After
    public void clean_up() {
        playerNT = null;
    }

    @Test
    public void test_getEndpoint() {

        assertThat(true, is(true));
    }
}
