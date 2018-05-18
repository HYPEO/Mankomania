package space.hypeo.networking.network;

/**
 * Created by pichlermarc on 18.05.2018.
 */
public interface IDeviceStatePublisher {
    void subscribe(IDeviceStateSubscriber subscriber);
}
