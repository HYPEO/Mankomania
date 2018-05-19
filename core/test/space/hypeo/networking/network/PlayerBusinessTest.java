package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PlayerBusinessTest {

    private PlayerBusiness playerBusiness;

    @Before
    public void setup() {
        playerBusiness = new PlayerBusiness("test_raw");
    }

    @After
    public void clean_up() {
        playerBusiness = null;
    }

    @Test
    public void test_not_equal() {
        assertThat(playerBusiness, not(new PlayerBusiness("another_raw")));
    }

    @Test
    public void test_equal() {
        assertEquals(playerBusiness, playerBusiness);
    }
}
