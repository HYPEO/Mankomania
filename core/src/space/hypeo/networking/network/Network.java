package space.hypeo.networking.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.HashMap;

import space.hypeo.networking.packages.Lobby;
import space.hypeo.networking.packages.Notification;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

/**
 * The class Network is a auxiliary class
 * to keep things common to both the client and server.
 */
public class Network {

    // this class is not instantiable!
    private Network() {}

    // communication ports
    public static final int PORT_TCP = 54555;
    public static final int PORT_UDP = 54777;

    // maximum number of players
    public static final int MAX_PLAYER = 5;

    // max time in milli-second to try to connect
    public static final int TIMEOUT_MS = 5000;

    /**
     * Register objects for server|client that are going to be sent over the network.
     * NOTE: all classes that are used in classes, have to be registered too!
     * @param endPoint can be server or client
     */
    public static void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
        kryo.register(Notification.class);
        kryo.register(java.util.HashMap.class);

        kryo.register(Lobby.class);
        kryo.register(space.hypeo.networking.network.Player.class);
        kryo.register(String.class);
        kryo.register(space.hypeo.networking.network.Role.class);

    }

}
