package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import space.hypeo.networking.endpoint.Endpoint;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkPlayerHostTest {

    /* class to test */
    @InjectMocks
    private NetworkPlayer networkPlayer;

    /* mocks for networkPlayer */
    @Mock private Endpoint endpoint;
    @Mock private RawPlayer rawPlayer;
    @Mock private Lobby lobby;

    private static final String PLAYER_ID= "ac03";
    private static final String NICKNAME = "a_test_RawPlayer";
    private static final String IP_ADDRESS = "192.168.1.99";
    private static final Role ROLE = Role.HOST;

    private RawPlayer getRawPlayerMock() {
        RawPlayer rpm = mock(RawPlayer.class);
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
        networkPlayer = new NetworkPlayer();
        endpoint = getEndpointMock();
        lobby = mock(Lobby.class);
        rawPlayer = getRawPlayerMock();
    }

    @After
    public void clean_up() {
        networkPlayer = null;
    }

    @Test
    public void test_() {
        assertThat(true, is(true));
    }
}
