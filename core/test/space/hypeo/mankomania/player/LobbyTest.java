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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

        /* init mock behavior */
        when( playerSkeleton.getPlayerID() ).thenReturn(PLAYER_ID + autoIncrementPlayerCounter);

        autoIncrementPlayerCounter++;
        return playerSkeleton;
    }

    private PlayerSkeleton getPlayerSkeletonMockColor(Color color) {
        PlayerSkeleton ps = mock(PlayerSkeleton.class);

        /* init mock behavior */
        //when(ps.getPlayerID()).thenReturn(PLAYER_ID + autoIncrementPlayerCounter);
        when(ps.getColor()).thenReturn(color);

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
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_clear_one() {
        lobby.add(playerSkeleton);
        lobby.clear();
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_zero() {
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_size_one() {
        lobby.add(playerSkeleton);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_zero() {
        lobby.remove(playerSkeleton);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_add_one() {
        lobby.add(playerSkeleton);
        assertThat(lobby.size(), is(1));
    }

    @Test
    public void test_remove_by_playerID() {
        lobby.add(playerSkeleton);
        lobby.remove(PLAYER_ID + 1);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_remove_by_object() {
        lobby.add(playerSkeleton);
        lobby.remove(playerSkeleton);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_contains_object_not() {
        assertThat(lobby.contains(playerSkeleton), not(playerSkeleton));
    }

    @Test
    public void test_contains_object() {
        lobby.add(playerSkeleton);
        assertThat(lobby.contains(playerSkeleton), is(true));
    }

    @Test
    public void test_contains_playerID_not() {
        assertThat(lobby.contains(PLAYER_ID + 1), not(playerSkeleton));
    }

    @Test
    public void test_contains_playerID() {
        lobby.add(playerSkeleton);
        assertThat(lobby.contains(PLAYER_ID + 1), is(playerSkeleton));
    }

    @Test
    public void test_isFull_not() {
        lobby.add(playerSkeleton);
        assertThat(lobby.isFull(), is(false));
    }

    @Test
    public void test_isFull() {
        lobby = new Lobby(1);
        assertThat(lobby.getMaxPlayer(), is(1));
        lobby.add(playerSkeleton);
        assertThat(lobby.isFull(), is(true));
    }

    @Test
    public void test_initStatus() {
        lobby.add(playerSkeleton);
        assertThat(lobby.getReadyStatus(playerSkeleton), is(false));
    }

    @Test
    public void test_toggleStatus_oneTime() {
        lobby.add(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);
        assertThat(lobby.getReadyStatus(playerSkeleton), is(true));
    }

    @Test
    public void test_toggleStatus_twoTimes() {
        lobby.add(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);
        assertThat(lobby.getReadyStatus(playerSkeleton), is(false));
    }

    @Test
    public void test_areAllPlayerReady_emptyLobby() {
        assertThat(lobby.areAllPlayerReady(), is(false));
    }

    @Test
    public void test_areAllPlayerReady_onePlayer() {
        lobby.add(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);
        assertThat(lobby.areAllPlayerReady(), is(true));
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_notReady() {
        lobby.add(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);

        PlayerSkeleton secondPlayer = getPlayerSkeletonMock();
        lobby.add(secondPlayer);

        assertThat(lobby.areAllPlayerReady(), is(false));
    }

    @Test
    public void test_areAllPlayerReady_twoPlayer_ready() {
        lobby.add(playerSkeleton);
        lobby.toggleReadyStatus(playerSkeleton);

        PlayerSkeleton secondPlayer = getPlayerSkeletonMock();
        lobby.add(secondPlayer);
        lobby.toggleReadyStatus(secondPlayer);

        assertThat(lobby.areAllPlayerReady(), is(true));
    }

    @Test
    public void test_setColorPlayerSkeleton() {
        Color color = mock(Color.class);
        PlayerSkeleton playerSkeleton = getPlayerSkeletonMockColor(color);
        playerSkeleton.setColor(color);

        verify(playerSkeleton).setColor(color);
    }

    @Test
    public void test_setColor() {
        Color color = mock(Color.class);
        PlayerSkeleton playerSkeleton = getPlayerSkeletonMockColor(color);

        lobby.add(playerSkeleton);
        lobby.setColor(playerSkeleton,color);

        verify(playerSkeleton).setColor(color);
    }

    @Test
    public void test_setColor_fails() {
        Color color1 = mock(Color.class);
        Color color2 = mock(Color.class);
        PlayerSkeleton playerSkeleton = getPlayerSkeletonMockColor(color1);

        lobby.add(playerSkeleton);
        lobby.setColor(playerSkeleton,color1);

        verify(playerSkeleton, never()).setColor(color2);
    }

    @Test
    public void test_usedColors() {
        Color color1 = mock(Color.class);
        Color color2 = mock(Color.class);

        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMockColor(color1);
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMockColor(color2);

        lobby.add(playerSkeleton1);
        lobby.add(playerSkeleton2);

        lobby.setColor(playerSkeleton1, color1);
        lobby.setColor(playerSkeleton2, color2);

        verify(playerSkeleton1).setColor(color1);
        verify(playerSkeleton2).setColor(color2);

        Set<Color> expectedColors =
                new HashSet<>(Arrays.asList(
                        color1, color2
                ));

        Set<Color> usedColorsInLobby = lobby.usedColors();

        assertEquals(usedColorsInLobby, expectedColors);
    }

    @Test
    public void test_getData() {
        PlayerSkeleton playerSkeleton1 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton2 = getPlayerSkeletonMock();
        PlayerSkeleton playerSkeleton3 = getPlayerSkeletonMock();

        lobby.add(playerSkeleton1);
        lobby.add(playerSkeleton2);
        lobby.add(playerSkeleton3);

        Set<PlayerSkeleton> expectedPlayerSkeleton =
                new HashSet<>(Arrays.asList(
                        playerSkeleton1, playerSkeleton2, playerSkeleton3
                ));

        assertEquals(lobby.getData(), expectedPlayerSkeleton);
    }

}
