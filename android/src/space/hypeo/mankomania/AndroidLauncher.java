package space.hypeo.mankomania;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.LinkedList;
import java.util.List;

import space.hypeo.networking.network.IDeviceStateSubscriber;
import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.network.IDeviceStatePublisher;

public class AndroidLauncher extends AndroidApplication implements IDeviceStatePublisher {

    Mankomania mankomania = null;
    List<IDeviceStateSubscriber> subscriberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        subscriberList = new LinkedList<>();
        mankomania = new Mankomania(this);
        initialize(mankomania, config);
    }

    @Override
    protected void onPause() {
        super.onPause();
        synchronized (this) {
            for (IDeviceStateSubscriber subscriber : subscriberList)
                subscriber.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        synchronized (this) {
            for (IDeviceStateSubscriber subscriber : subscriberList)
                subscriber.onStop();
        }
    }

    @Override
    public void subscribe(IDeviceStateSubscriber subscriber) {
        synchronized (this) {
            subscriberList.add(subscriber);
        }
    }
}
