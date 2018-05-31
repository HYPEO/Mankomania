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

    // this class is not instantiable!
    private NetworkAddress() {}

    /**
     * regex that matches network addresses
     */
    public static final String IPADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    /**
     * Validates the IP Address.
     * @param ipAddress given IP Address
     * @return IP address if valide
     *          null if IP address violates pattern
     */
    public static String validateIpAddress(String ipAddress) {
        String validIpAddress = null;

        Log.info("validate IP Address " + ipAddress);

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);

        if(matcher.find()) {
            validIpAddress = matcher.group();
        };

        if(validIpAddress != null) {
            Log.info("validIpAddress: " + validIpAddress);
        } else {
            Log.info(ipAddress + " violates IP address pattern");
        }

        return validIpAddress;
    }

    /**
     * Gets all available network addresses for a device.
     * Filters loopback and inactive addresses out.
     * @return List of available network addresses.
     * @throws SocketException
     */
    public static List<InetAddress> getAllAvailableNetworkAddresses() throws SocketException {

        List<InetAddress> availableAddresses = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            Log.info("Available Network-Addresses:");

            while( interfaces.hasMoreElements() ) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if( iface.isLoopback() || !iface.isUp() ) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while( addresses.hasMoreElements() ) {
                    InetAddress addr = addresses.nextElement();

                    Log.info("  " + addr.getHostAddress() );

                    availableAddresses.add(addr);
                }
            }
        } catch( SocketException e ) {
            Log.error(e.getMessage());
            throw e;
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

        try {
            List<InetAddress> ipAddresses = NetworkAddress.getAllAvailableNetworkAddresses();

            for( InetAddress addr : ipAddresses ) {
                ip = validateIpAddress(addr.getHostAddress().toString());
                if(ip != null) {
                    break;
                }
            }

        } catch( SocketException e ) {
            Log.error(e.getMessage());
            throw e;
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
