package space.hypeo.networking.network;


/**
 * This class represents the role of player concerning the connection.
 */
public class Role {

    // string representation of the connection.
    private String id;

    // it is not possible to create new constants from outside.
    private Role(String id) {
        this.id = id;
    }

    // TODO: public constructor is needed for network-registration, BUT unwanted
    public Role() { this(STR_NOT_CONNECTED); }

    private static final String STR_CLIENT = "CLIENT";
    private static final String STR_HOST = "HOST";
    private static final String STR_NOT_CONNECTED = "NOT_CONNECTED";

    public static final Role HOST = new Role(STR_HOST);
    public static final Role CLIENT = new Role(STR_CLIENT);
    public static final Role NOT_CONNECTED = new Role(STR_NOT_CONNECTED);

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) { return false; }
        if( o == this) { return true; }

        if( !(o instanceof Role) ) { return false; }

        Role other = (Role) o;
        return id == other.id;
    }

}
