package space.hypeo.networking.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class RoleTest {
    private Role rNotConnected;
    private Role rHost;
    private Role rClient;

    @Before
    public void setup() {
        rNotConnected = new Role();
        rHost = Role.HOST;
        rClient = Role.CLIENT;
    }

    @After
    public void clean_up() {
        rNotConnected = null;
        rHost = null;
        rClient = null;
    }

    @Test
    public void test_NotConnected_equal_NotConnected() {
        assertThat(rNotConnected, is(Role.NOT_CONNECTED));
    }

    @Test
    public void test_NotConnected_equal_NotConnected_2() {
        assertThat(rNotConnected, is(new Role()));
    }

    @Test
    public void test_NotConnected_not_equal_Host() {
        assertThat(rNotConnected, not(Role.HOST));
    }

    @Test
    public void test_NotConnected_not_equal_Host_2() {
        assertThat(rNotConnected, not(rHost));
    }

    @Test
    public void test_NotConnected_not_equal_Client() {
        assertThat(rNotConnected, not(Role.CLIENT));
    }

    @Test
    public void test_NotConnected_not_equal_Client_2() {
        assertThat(rNotConnected, not(rClient));
    }
}
