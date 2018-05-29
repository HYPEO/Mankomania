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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        assertEquals(matcher.find(), true);
    }

    @Test
    public void test_getPlayerID() {
        assertEquals(playerSkeleton.getPlayerID().length(), 4);
    }

    @Test
    public void test_getNickname() {
        assertEquals(playerSkeleton.getNickname(), NICKNAME);
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

}
