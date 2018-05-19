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
    private PlayerBusiness playerBusiness;

    private final int MAX_PLAYER = 5;
    private int autoIncrementPlayerCounter;
    private final String PLAYER_ID= "ID_";
    /*private final String NICKNAME = "NICK_";
    private final String IP_ADDRESS = "192.168.1.";*/

    @Before
    public void setup() {
        autoIncrementPlayerCounter = 1;
        lobby = new Lobby(MAX_PLAYER);
        playerBusiness = getRawPlayerMock();
    }

    @After
    public void clean_up() {
        lobby = null;
    }

    private PlayerBusiness getRawPlayerMock() {
        PlayerBusiness rp = mock(PlayerBusiness.class);

        /* init mock behavior */
        when( rp.getPlayerID() ).thenReturn(PLAYER_ID + autoIncrementPlayerCounter);
        /*when( rp.getNickname() ).thenReturn(NICKNAME + autoIncrementPlayerCounter);
        when( rp.getAddress() ).thenReturn(IP_ADDRESS + autoIncrementPlayerCounter);*/

        autoIncrementPlayerCounter++;
        return rp;
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
        lobby.add(playerBusiness);
        lobby.clear();
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_zero() {
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_one() {
        lobby.add(playerBusiness);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_zero() {
        lobby.remove(playerBusiness);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_add_one() {
        lobby.add(playerBusiness);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_by_playerID() {
        lobby.add(playerBusiness);
        lobby.remove(PLAYER_ID + 1);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_remove_by_object() {
        lobby.add(playerBusiness);
        lobby.remove(playerBusiness);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_contains_object_not() {
        assertThat(lobby.contains(playerBusiness), not(playerBusiness));
    }

    @Test
    public void test_contains_object() {
        lobby.add(playerBusiness);
        assertThat(lobby.contains(playerBusiness), is(true));
    }

    @Test
    public void test_contains_playerID_not() {
        assertThat(lobby.contains(PLAYER_ID + 1), not(playerBusiness));
    }

    @Test
    public void test_contains_playerID() {
        lobby.add(playerBusiness);
        assertThat(lobby.contains(PLAYER_ID + 1), is(playerBusiness));
    }

    @Test
    public void test_isFull_not() {
        lobby.add(playerBusiness);
        assertThat(lobby.isFull(), is(false));
    }

    @Test
    public void test_isFull() {
        lobby = new Lobby(1);
        assertThat(lobby.getMaxPlayer(), is(1));
        lobby.add(playerBusiness);
        assertThat(lobby.isFull(), is(true));
    }

    @Test
    public void test_initStatus() {
        lobby.add(playerBusiness);
        assertThat(lobby.getStatus(playerBusiness), is(false));
    }

    @Test
    public void test_toggleStatus_oneTime() {
        lobby.add(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);
        assertThat(lobby.getStatus(playerBusiness), is(true));
    }

    @Test
    public void test_toggleStatus_twoTimes() {
        lobby.add(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);
        assertThat(lobby.getStatus(playerBusiness), is(false));
    }

    @Test
    public void test_areAllPlayerReady_emptyLobby() {
        assertThat(lobby.areAllPlayerReady(), is(false));
    }

    @Test
    public void test_areAllPlayerReady_onePlayer() {
        lobby.add(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);
        assertThat(lobby.areAllPlayerReady(), is(true));
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_notReady() {
        lobby.add(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);

        PlayerBusiness secondPlayer = getRawPlayerMock();
        lobby.add(secondPlayer);

        assertThat(lobby.areAllPlayerReady(), is(false));
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_ready() {
        lobby.add(playerBusiness);
        lobby.toggleReadyStatus(playerBusiness);

        PlayerBusiness secondPlayer = getRawPlayerMock();
        lobby.add(secondPlayer);
        lobby.toggleReadyStatus(secondPlayer);

        assertThat(lobby.areAllPlayerReady(), is(true));
    }
}
