package space.hypeo.networking.network;

import space.hypeo.networking.packages.PingRequest;

import static space.hypeo.networking.network.CRole.Role.CLIENT;
import static space.hypeo.networking.network.CRole.Role.HOST;
import static space.hypeo.networking.network.CRole.Role.NOT_CONNECTED;

/**
 * This class represents the role of player concerning the connection.
 */
public class CRole {

    private final String STR_CLIENT = "CLIENT";
    private final String STR_HOST = "HOST";
    private final String STR_NOT_CONNECTED = "NOT_CONNECTED";
    private final String STR_UNKNOWN_ROLE = "UNKNOWN_ROLE";

    public enum Role { HOST, CLIENT, NOT_CONNECTED;
    };

    // current Role of current P
    private Role role;

    public CRole() {
        new CRole(Role.NOT_CONNECTED);
    }

    public CRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) { return false; }
        if( o == this) { return true; }

        if( o instanceof Role ) {
            Role other = (Role) o;
            return role == other;
        } else if( o instanceof CRole ) {
            CRole other = (CRole) o;
            return role.equals( other.getRole() );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        if( role == HOST ) {
            return STR_HOST;
        } else if( role == CLIENT ) {
            return STR_CLIENT;
        } else if( role == NOT_CONNECTED ) {
            return STR_NOT_CONNECTED;
        } else {
            return STR_UNKNOWN_ROLE;
        }
    }
}
