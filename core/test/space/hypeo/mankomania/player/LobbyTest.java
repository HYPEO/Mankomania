package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LobbyTest {

    /* class to test */
    @InjectMocks
    private Lobby lobby;

    /* the mock for the class, that will be tested */
    @Mock
    private PlayerSkeleton playerSkeleton;

    private final int MAX_PLAYER = 5;
    private int autoIncrementPlayerCounter;
    private final String PLAYER_ID= "ID_";

    @Before
    public void setup() {
        autoIncrementPlayerCounter = 1;
        lobby = new Lobby(MAX_PLAYER);
        playerSkeleton = getPlayerSkeletonMock();
    }

    @After
    public void clean_up() {
        lobby = null;
    }

    private PlayerSkeleton getPlayerSkeletonMock() {
        PlayerSkeleton playerSkeleton = mock(PlayerSkeleton.class);

        //when( playerSkeleton.getPlayerID() ).thenReturn(PLAYER_ID + autoIncrementPlayerCounter);

        autoIncrementPlayerCounter++;
        return playerSkeleton;
    }

    private PlayerSkeleton getPlayerSkeletonMockColor(Color color) {
        PlayerSkeleton ps = mock(PlayerSkeleton.class);

        when(ps.getColor()).thenReturn(color);

        autoIncrementPlayerCounter++;
        return ps;
    }

    private PlayerSkeleton getPlayerSkeletonMockActive(boolean isActive) {
        PlayerSkeleton ps = mock(PlayerSkeleton.class);

        when(ps.isActive()).thenReturn(isActive);

        autoIncrementPlayerCounter++;
        return ps;
    }

    private PlayerSkeleton getPlayerSkeletonMockBalance() {
        PlayerSkeleton ps = mock(PlayerSkeleton.class);

        when(ps.getBalance()).thenReturn(PlayerFactory.START_BALANCE);

        autoIncrementPlayerCounter++;
        return ps;
    }

    private PlayerSkeleton getPlayerSkeletonMockReady(boolean isReady) {
        PlayerSkeleton ps = mock(PlayerSkeleton.class);

        when(ps.isReady()).thenReturn(isReady);

        autoIncrementPlayerCounter++;
        return ps;
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
        assertEquals(0, lobby.size());
    }

    @Test
    public void test_clear_one() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        lobby.clear();
        assertEquals(0, lobby.size());
    }

    @Test
    public void test_size_zero() {
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_one() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(1, lobby.size());
    }

    @Test
    public void test_remove_zero() {
        lobby.remove(playerSkeleton);
        assertEquals(0, lobby.size());
    }

    @Test
    public void test_add_one() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(1, lobby.size());
    }

    @Test
    public void test_remove_by_playerID() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        lobby.remove(PLAYER_ID + 1);
        assertEquals(0, lobby.size());
    }

    @Test
    public void test_remove_by_object() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        lobby.remove(PLAYER_ID + 1);
        assertEquals(0, lobby.size());
    }

    @Test
    public void test_contains_object_not() {
        assertThat(lobby.contains(playerSkeleton), not(playerSkeleton));
    }

    @Test
    public void test_contains_object() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(true, lobby.contains(playerSkeleton));
    }

    @Test
    public void test_contains_playerID_not() {
        assertThat(lobby.contains(PLAYER_ID + 1), not(playerSkeleton));
    }

    @Test
    public void test_contains_playerID() {
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(lobby.contains(PLAYER_ID + 1), true);
    }

    @Test
    public void test_isFull_not() {
        lobby.put(PLAYER_ID +1, playerSkeleton);
        assertEquals(false, lobby.isFull());
    }

    @Test
    public void test_isFull() {
        lobby = new Lobby(1);
        assertThat(lobby.getMaxPlayer(), is(1));
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(true, lobby.isFull());
    }

    @Test
    public void test_areAllPlayerReady_emptyLobby() {
        assertEquals(false, lobby.areAllPlayerReady());
    }

    @Test
    public void test_areAllPlayerReady_onePlayer() {
        PlayerSkeleton playerSkeleton = getPlayerSkeletonMockReady(true);
        lobby.put(PLAYER_ID + 1, playerSkeleton);
        assertEquals(true, lobby.areAllPlayerReady());
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_notReady() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMockReady(true);
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMockReady(false);
        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        assertEquals(false, lobby.areAllPlayerReady());
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_ready() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMockReady(true);
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMockReady(true);
        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        assertEquals(true, lobby.areAllPlayerReady());
    }

    @Test
    public void test_usedColors() {
        Color color1 = mock(Color.class);
        Color color2 = mock(Color.class);

        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMockColor(color1);
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMockColor(color2);

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        Set<Color> expectedColors =
                new HashSet<>(Arrays.asList(
                        color1, color2
                ));

        Set<Color> usedColorsInLobby = lobby.usedColors();

        assertEquals(expectedColors, usedColorsInLobby);
    }

    @Test
    public void test_keySet() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton3 = getPlayerSkeletonMock();

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);
        lobby.put(PLAYER_ID + 3, playerSkeleton3);

        Set<String> expectedPlayerIds =
                new HashSet<>(Arrays.asList(
                        PLAYER_ID + 1, PLAYER_ID + 2, PLAYER_ID + 3
                ));

        assertEquals(expectedPlayerIds, lobby.keySet());
    }

    @Test
    public void test_values() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton3 = getPlayerSkeletonMock();

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);
        lobby.put(PLAYER_ID + 3, playerSkeleton3);

        Set<PlayerSkeleton> expectedPlayerSkeleton =
                new HashSet<>(Arrays.asList(
                        playerSkeleton1, playerSkeleton2, playerSkeleton3
                ));

        assertEquals(expectedPlayerSkeleton, new HashSet<PlayerSkeleton>(lobby.values()));
    }


    @Test
    public void test_replacePlayerSkeleton() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMockColor(Color.RED);
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        when(playerSkeleton1.getColor()).thenReturn(Color.BLUE);

        lobby.put(PLAYER_ID + 1, playerSkeleton1);

        assertEquals(Color.BLUE, lobby.get(PLAYER_ID + 1).getColor());
    }

    @Test
    public void test_getPlayerSkeleton_byId_found() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        assertEquals(playerSkeleton1, lobby.get(PLAYER_ID + 1));
    }

    @Test
    public void test_getPlayerSkeleton_byId_notFound() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton3 = getPlayerSkeletonMock();

        lobby.put(PLAYER_ID + 1, playerSkeleton1);
        lobby.put(PLAYER_ID + 2, playerSkeleton2);

        assertEquals(null, lobby.get(PLAYER_ID + 3));
    }
}
