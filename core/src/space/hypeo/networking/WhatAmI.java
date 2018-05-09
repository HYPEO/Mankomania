package space.hypeo.networking;

import space.hypeo.networking.client.MClient;
import space.hypeo.networking.host.MHost;
import space.hypeo.networking.network.Network;

/**
 * This class stores the role of a player in the network:
 * The Player can check, if s/he is HOST|CLIENT|NOT_CONNECTED.
 * The class is implemented as singleton.
 */
public class WhatAmI {

    // only one instance of WhatAmI
    private static WhatAmI instance;

    // initial value = NOT_CONNECTED
    private static Network.Role role = Network.Role.NOT_CONNECTED;

    private WhatAmI() { }

    public static WhatAmI getInstance() {
        if( instance == null ) {
            instance = new WhatAmI();
        }
        return instance;
    }

    /**
     * Sets the current role of the current player.
     * @param role
     */
    public void setRole(Network.Role role) {
        // change role only if it's NOT_CONNECTED
        if( role == Network.Role.NOT_CONNECTED ) {
            this.role = role;
        }
    }

    /**
     * Gets the current role of the current player.
     * @return HOST|CLIENT|NOT_CONNECTED
     */
    public Network.Role getRole() {
        return role;
    }

    /**
     * Gets the instance of the specified endpoint.
     * @return instance of host|client OR null if not connected.
     */
    /*public Endpoint getEndpoint() {

        if( role == Network.Role.HOST ) {
            return MHost.getInstance();

        } else if( role == Network.Role.CLIENT ) {
            return MClient.getInstance();
        }

        return null;
    }*/
}
