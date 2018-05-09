package space.hypeo.networking;

import space.hypeo.networking.client.MClient;
import space.hypeo.networking.host.MHost;
import space.hypeo.networking.network.Network;

public class WhatAmI {

    // only one instance of WhatAmI
    private static WhatAmI instance = null;

    // initial value = NOT_CONNECTED
    private static Network.Role role = Network.Role.NOT_CONNECTED;

    private WhatAmI() { }

    public WhatAmI getIntstance() {
        if( instance == null ) {
            instance = new WhatAmI();
        }
        return instance;
    }

    public void init(Network.Role role) {
        // init role only once
        if( role == Network.Role.NOT_CONNECTED ) {
            this.role = role;
        }
    }

    public Network.Role getRole() {
        return role;
    }

    public void setNotConnected() {
        role = Network.Role.NOT_CONNECTED;
    }

    public Endpoint getEndpoint() {

        if( role == Network.Role.HOST ) {
            return MHost.getInstance();

        } else if( role == Network.Role.CLIENT ) {
            return MClient.getInstance();
        }

        return null;
    }
}
