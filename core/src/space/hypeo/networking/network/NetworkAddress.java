package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class provides methods to work with network addresses for an endpoint.
 * https://stackoverflow.com/questions/8083479/java-getting-my-ip-address
 */
public final class NetworkAddress {

    /**
     * regex that matches network addresses
     */
    public static final String IPADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    /**
     * Gets all available network addresses for a device.
     * Filters loopback and inactive addresses out.
     * @return List of available network addresses.
     * @throws SocketException
     */
    public static List<InetAddress> getAllAvailableNetworkAddresses() throws SocketException {

        List<InetAddress> availableAddresses = new ArrayList<InetAddress>();

        String ip;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while( interfaces.hasMoreElements() ) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if( iface.isLoopback() || !iface.isUp() ) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while( addresses.hasMoreElements() ) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    //Log.info(iface.getDisplayName() + " " + ip);

                    availableAddresses.add(addr);
                }
            }
        } catch( SocketException e ) {
            throw new RuntimeException(e);
        }

        return availableAddresses;
    }

    /**
     * Gets the current IP address of device.
     * @return String IP address.
     * @throws SocketException
     */
    public static String getNetworkAddress() throws SocketException {

        String ip = "";

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);

        try {
            List<InetAddress> ipAddresses = NetworkAddress.getAllAvailableNetworkAddresses();

            for( InetAddress addr : ipAddresses ) {
                Matcher matcher = pattern.matcher(addr.getHostAddress());

                if( matcher.find() ) {
                    ip = matcher.group();
                }
            }

        } catch( SocketException e ) {
            throw new RuntimeException(e);
        }

        return ip;
    }

    /**
     * Filters loopback and inactive addresses out of given list of addresses.
     * @param addresses list to filter on.
     * @return addresses without loopback.
     */
    public static List<InetAddress> filterLoopback( List<InetAddress> addresses ) {

        for( Iterator<InetAddress> addr = addresses.listIterator(); addr.hasNext(); ) {
            InetAddress a = addr.next();
            if( a.isLoopbackAddress() ) {
                addr.remove();
            }
        }

        return addresses;
    }
}
