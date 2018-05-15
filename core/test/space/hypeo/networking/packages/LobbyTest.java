package space.hypeo.networking.packages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import space.hypeo.networking.network.RawPlayer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LobbyTest {

    private Lobby lobby;
    private final String NICKNAME = "a_test_RawPlayer";
    private String playerID;
    private RawPlayer rawPlayer;

    @Before
    public void setup() {
        lobby = new Lobby();
        rawPlayer = new RawPlayer(NICKNAME);
        playerID = rawPlayer.getPlayerID();
    }

    @After
    public void clean_up() {
        lobby = null;
        rawPlayer = null;
    }

    @Test
    public void test_isEmpty() {
        assertThat(lobby.isEmpty(), is(true));
    }

    @Test
    public void test_isNotEmpty() {
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
        lobby.remove(playerID);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_remove_by_object() {
        lobby.add(rawPlayer);
        lobby.remove(rawPlayer);
        assertThat(lobby.size(), is(0));
    }

    @Test
    public void test_contains_object_fails() {
        assertThat(lobby.contains(rawPlayer), not(rawPlayer));
    }

    @Test
    public void test_contains_object() {
        lobby.add(rawPlayer);
        assertThat(lobby.contains(rawPlayer), is(true));
    }

    @Test
    public void test_contains_playerID_fails() {
        assertThat(lobby.contains(playerID), not(rawPlayer));
    }

    @Test
    public void test_contains_playerID() {
        lobby.add(rawPlayer);
        assertThat(lobby.contains(playerID), is(rawPlayer));
    }
}
