package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import org.junit.Test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NetworkAddressTest {

    private String ip = "192.168.1.1";
    private String prefix = "akdfjasölk flkasdj f";
    private String postfix = "adi,m,e ka sdölkfj aö8985454 54555 4";

    @Test
    public void test_validateIpAddress_pass() {
        assertEquals(NetworkAddress.validateIpAddress(ip), ip);
    }

    @Test
    public void test_validateIpAddress_regex_pass() {
        assertEquals(NetworkAddress.validateIpAddress(prefix+ip+postfix), ip);
    }

    @Test
    public void test_validateIpAddress_fail() {
        assertThat(NetworkAddress.validateIpAddress(prefix), not(ip));
    }

    @Test
    public void test_getAllAvailableNetworkAddresses_notEmpty() {
        try {
            assertEquals(false, NetworkAddress.getAllAvailableNetworkAddresses().isEmpty());
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }
    }

    @Test
    public void test_getNetworkAddress() {
        try {
            assertEquals(false, NetworkAddress.getNetworkAddress().isEmpty());
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }
    }

    @Test
    public void test_filterLoopback_localhost() {
        List<InetAddress> addresses = new ArrayList<>();

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException e) {
            Log.info(e.getMessage());
        }
        addresses.add(inetAddress);

        assertEquals(0, NetworkAddress.filterLoopback(addresses).size());
    }
}
