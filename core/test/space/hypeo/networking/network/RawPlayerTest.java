package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class RawPlayerTest {

    RawPlayer rawPlayer;

    @Before
    public void setup() {
        rawPlayer = new RawPlayer("test_raw");
    }

    @After
    public void clean_up() {
        rawPlayer = null;
    }

    @Test
    public void test_not_equal() {
        assertThat(rawPlayer, not(new RawPlayer("another_raw")));
    }

    @Test
    public void test_equal() {
        assertEquals(rawPlayer, rawPlayer);
    }
}
