package space.hypeo.networking;

import com.esotericsoftware.minlog.Log;

import space.hypeo.networking.network.CRole;

/**
 * This class stores the role of a player in the network:
 * The Player can check, if s/he is HOST|CLIENT|NOT_CONNECTED.
 * The class is implemented as singleton.
 */
public class WhatAmI {

    // only one instance of WhatAmI
    private static WhatAmI instance;

    // only one role per player
    private CRole role = new CRole();

    private WhatAmI() { }

    public static WhatAmI getInstance() {
        if( instance == null ) {
            instance = new WhatAmI();
        }
        return instance;
    }

    /**
     * Sets the current role of the current player.
     * @param r
     */
    public void setRole(CRole.Role r) {
        // change role only if it's NOT_CONNECTED
        if( r.equals(CRole.Role.NOT_CONNECTED) ) {
            role.setRole(r);
        }
        Log.info("WhatAmI: I'm a " + role.toString());
    }

    /**
     * Gets the current role of the current player.
     * @return HOST|CLIENT|NOT_CONNECTED
     */
    public CRole.Role getRole() {
        return role.getRole();
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
