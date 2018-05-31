package space.hypeo.mankomania.player;

import com.badlogic.gdx.graphics.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import space.hypeo.networking.network.NetworkAddress;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PlayerSkeletonTest {

    private PlayerSkeleton playerSkeleton;
    private static final String NICKNAME = "testPlayer";

    @Before
    public void setup() {
        playerSkeleton = new PlayerSkeleton(NICKNAME);
    }

    @After
    public void clean_up() {
        playerSkeleton = null;
    }

    @Test
    public void test_getAddress() {
        Pattern pattern = Pattern.compile(NetworkAddress.IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(playerSkeleton.getAddress());
        assertEquals(true, matcher.find());
    }

    @Test
    public void test_getPlayerID() {
        assertEquals(4, playerSkeleton.getPlayerID().length());
    }

    @Test
    public void test_getNickname() {
        assertEquals(NICKNAME, playerSkeleton.getNickname());
    }

    @Test
    public void test_not_equal() {
        assertThat(playerSkeleton, not(new PlayerSkeleton(NICKNAME + 1)));
    }

    @Test
    public void test_not_equal_2() {
        assertThat(playerSkeleton, not(new PlayerSkeleton(NICKNAME)));
    }

    @Test
    public void test_equal() {
        assertEquals(playerSkeleton, playerSkeleton);
    }

    @Test
    public void test_color() {
        Color color = mock(Color.class);
        playerSkeleton.setColor(color);
        assertEquals(playerSkeleton.getColor(), color);
    }

    @Test
    public void test_isActive_true() {
        playerSkeleton.setActive(true);
        assertEquals(true, playerSkeleton.isActive());
    }

    @Test
    public void test_isActive_false() {
        playerSkeleton.setActive(false);
        assertEquals(false, playerSkeleton.isActive());
    }

    @Test
    public void test_getBalance() {
        assertEquals(PlayerFactory.START_BALANCE, playerSkeleton.getBalance());
    }

    @Test
    public void test_setBalance() {
        playerSkeleton.setBalance(1);
        assertEquals(1, playerSkeleton.getBalance());
    }
}
