package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.player.PlayerNT;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerManagerTest {

    @InjectMocks
    private PlayerManager playerManager;

    @Mock private StageManager stageManager;
    @Mock private StageFactory stageFactory;
    @Mock private PlayerSkeleton playerSkeleton;
    @Mock private PlayerNT playerNT;
    @Mock private Role role;
    @Mock private Lobby lobby;

    @Before
    public void setup() {
        stageManager = mock(StageManager.class);
        stageFactory = mock(StageFactory.class);
        playerSkeleton = mock(PlayerSkeleton.class);
        playerNT = mock(PlayerNT.class);
        role = mock(Role.class);
        lobby = mock(Lobby.class);
        playerManager = new PlayerManager(stageManager, stageFactory, role);
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
    public void test_setLobby_updatePlayerSkeleton() {
        String id = "test_id";

        when(playerSkeleton.getPlayerID()).thenReturn(id);
        when(playerSkeleton.getBalance()).thenReturn(10).thenReturn(20);

        when(lobby.isEmpty()).thenReturn(false);
        when(lobby.get(id)).thenReturn(playerSkeleton);

        playerManager.setPlayerSkeleton(playerSkeleton);
        playerManager.setLobby(lobby);

        verify(playerSkeleton).setBalance(any(Integer.class));
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
        when(playerSkeleton.isReady()).thenReturn(true);

        assertEquals(true, playerManager.isReady2startGame());
    }

    @Test
    public void test_toggleReadyStatus() {
        playerManager.setPlayerSkeleton(playerSkeleton);
        playerManager.setPlayerNT(playerNT);
        when(playerSkeleton.isReady()).thenReturn(true);
        playerManager.toggleReadyStatus();
        verify(playerSkeleton).setReady(false);
        verify(playerNT).broadCastLobby();
        verify(playerNT).broadCastLobby();
        verify(stageManager).getCurrentStage();
    }

    @Test
    public void test_usedPlayerColors() {
        playerManager.setLobby(lobby);
        playerManager.usedPlayerColors();
        verify(lobby).usedColors();
    }

    @Test
    public void test_setColor() {
        playerManager.setPlayerSkeleton(playerSkeleton);
        playerManager.setPlayerNT(playerNT);
        playerManager.setColor(Color.RED);
        when(playerSkeleton.getColor()).thenReturn(Color.RED);

        assertEquals(Color.RED, playerManager.getPlayerSkeleton().getColor());
        verify(playerNT).broadCastLobby();
    }

    @Test
    public void test_changeBalance_ownBalance() {
        playerManager.setPlayerSkeleton(playerSkeleton);
        playerManager.setPlayerNT(playerNT);

        String id = "test_id";
        when(playerSkeleton.getPlayerID()).thenReturn(id);

    }
}
