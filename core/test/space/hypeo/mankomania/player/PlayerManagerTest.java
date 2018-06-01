package space.hypeo.mankomania.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.hypeo.mankomania.StageManager;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.player.PlayerNT;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerManagerTest {

    @InjectMocks
    private PlayerManager playerManager;

    @Mock private StageManager stageManager;
    @Mock private PlayerSkeleton playerSkeleton;
    @Mock private PlayerNT playerNT;
    @Mock private Role role;
    @Mock private Lobby lobby;

    @Before
    public void setup() {
        stageManager = mock(StageManager.class);
        playerSkeleton = mock(PlayerSkeleton.class);
        playerNT = mock(PlayerNT.class);
        role = mock(Role.class);
        lobby = mock(Lobby.class);
        playerManager = new PlayerManager(stageManager, role);
    }

    @After
    public void clean_up() {
        playerManager = null;
    }

    @Test
    public void test_getRole() {
        assertEquals(role, playerManager.getRole());
    }

    @Test
    public void test_setLobby_getLobby() {
        playerManager.setLobby(lobby);
        assertEquals(lobby, playerManager.getLobby());
    }

    @Test
    public void test_setPlayerSkeleton_getPlayerSkeleton() {
        playerManager.setPlayerSkeleton(playerSkeleton);
        assertEquals(playerSkeleton, playerManager.getPlayerSkeleton());
    }

    @Test
    public void test_setPlayerNT_getPlayerNT() {
        playerManager.setPlayerNT(playerNT);
        assertEquals(playerNT, playerManager.getPlayerNT());
    }

    @Test
    public void test_isReady2startGame() {
        playerManager.setPlayerSkeleton(playerSkeleton);
        when(lobby.getReadyStatus(playerSkeleton)).thenReturn(true);
        playerManager.setLobby(lobby);
        assertEquals(true, playerManager.isReady2startGame());
    }

    @Test
    public void test_toggleReadyStatus() {
        playerManager.setPlayerSkeleton(playerSkeleton);

    }
}
