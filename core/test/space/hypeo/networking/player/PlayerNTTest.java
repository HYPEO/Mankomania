package space.hypeo.networking.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.endpoint.IHostConnector;

import static org.junit.Assert.*;
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
        assertEquals(endpoint, playerNT.getEndpoint());
    }

    /*
    @Test
    public void test_endTurn() {
        // TODO:
    }
    */

    @Test
    public void test_onPause() {
        playerNT.onPause();
        verify(endpoint).stop();
    }

    @Test
    public void test_onStop() {
        playerNT.onStop();
        verify(endpoint).stop();
    }

    @Test
    public void test_broadCastLobby() {
        playerNT.broadCastLobby();
        verify(endpoint).broadCastLobby();
    }

    @Test
    public void test_kickPlayerFromLobby() {
        // TODO: make the method testable!
        IHostConnector host = mock(IHostConnector.class);
        PlayerSkeleton playerSkeleton = mock(PlayerSkeleton.class);

        host.sendOrderToCloseConnection(playerSkeleton);
        verify(host).sendOrderToCloseConnection(playerSkeleton);
    }

}
