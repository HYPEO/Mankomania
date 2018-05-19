package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import space.hypeo.networking.endpoint.Endpoint;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PlayerNTHostTest {

    /* class to test */
    @InjectMocks
    private PlayerNT playerNT;

    /* mocks for playerNT */
    @Mock private Endpoint endpoint;
    @Mock private PlayerBusiness playerBusiness;
    @Mock private Lobby lobby;

    private static final String PLAYER_ID= "ac03";
    private static final String NICKNAME = "a_test_RawPlayer";
    private static final String IP_ADDRESS = "192.168.1.99";
    private static final Role ROLE = Role.HOST;

    private PlayerBusiness getRawPlayerMock() {
        PlayerBusiness rpm = mock(PlayerBusiness.class);
        //when( rpm.getPlayerID() ).thenReturn(PLAYER_ID);
        //when( rpm.getNickname() ).thenReturn(NICKNAME);
        //when( rpm.getAddress() ).thenReturn(IP_ADDRESS);
        return rpm;
    }

    private Lobby getLobbyMock() {
        Lobby l = mock(Lobby.class);
        return l;
    }

    private Endpoint getEndpointMock() {
        Endpoint ep = mock(Endpoint.class);
        //when( ep.getRole() ).thenReturn(ROLE);
        return ep;
    }

    @Before
    public void setup() {
        playerNT = new PlayerNT();
        endpoint = getEndpointMock();
        lobby = mock(Lobby.class);
        playerBusiness = getRawPlayerMock();
    }

    @After
    public void clean_up() {
        playerNT = null;
    }

    @Test
    public void test_() {
        assertThat(true, is(true));
    }
}
