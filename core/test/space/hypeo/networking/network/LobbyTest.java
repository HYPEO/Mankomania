package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LobbyTest {

    /* class to test */
    @InjectMocks
    private Lobby lobby;

    /* the mock for the class, that will be tested */
    @Mock
    private RawPlayer rawPlayer;

    private final int MAX_PLAYER = 5;
    private final String PLAYER_ID= "ac03";
    //private final String NICKNAME = "a_test_RawPlayer";
    //private final String IP_ADDRESS = "192.168.1.99";

    @Before
    public void setup() {

        lobby = new Lobby(MAX_PLAYER);
        rawPlayer = mock(RawPlayer.class);

        /* init mock behavior */
        when( rawPlayer.getPlayerID() ).thenReturn(PLAYER_ID);
        //when( rawPlayer.getNickname() ).thenReturn(NICKNAME);
        //when( rawPlayer.getAddress() ).thenReturn(IP_ADDRESS);
    }

    @After
    public void clean_up() {
        lobby = null;
    }


    @Test
    public void test_isEmpty() {
        assertThat(lobby.isEmpty(), is(true));
    }


    @Test
    public void test_isEmpty_not() {
        assertThat(!lobby.isEmpty(), is(false));
    }

    @Test
    public void test_clear_zero() {
        lobby.clear();
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_clear_one() {
        lobby.add(rawPlayer);
        lobby.clear();
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_zero() {
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_one() {
        lobby.add(rawPlayer);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_zero() {
        lobby.remove(rawPlayer);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_add_one() {
        lobby.add(rawPlayer);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_by_playerID() {
        lobby.add(rawPlayer);
        lobby.remove(PLAYER_ID);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_remove_by_object() {
        lobby.add(rawPlayer);
        lobby.remove(rawPlayer);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_contains_object_not() {
        assertThat(lobby.contains(rawPlayer), not(rawPlayer));
    }

    @Test
    public void test_contains_object() {
        lobby.add(rawPlayer);
        assertThat(lobby.contains(rawPlayer), is(true));
    }

    @Test
    public void test_contains_playerID_not() {
        assertThat(lobby.contains(PLAYER_ID), not(rawPlayer));
    }

    @Test
    public void test_contains_playerID() {
        lobby.add(rawPlayer);
        assertThat(lobby.contains(PLAYER_ID), is(rawPlayer));
    }

    @Test
    public void test_isFull_not() {
        lobby.add(rawPlayer);
        assertThat(lobby.isFull(), is(false));
    }

    @Test
    public void test_isFull() {
        lobby = new Lobby(1);
        assertThat(lobby.getMaxPlayer(), is(1));
        lobby.add(rawPlayer);
        assertThat(lobby.isFull(), is(true));
    }
}
