package space.hypeo.mankomania;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.Role;

public class AndroidLauncher extends AndroidApplication {

	Mankomania mankomania = null;

	private NetworkPlayer networkPlayer = null;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		mankomania = new Mankomania(networkPlayer);
		initialize(mankomania, config);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if( networkPlayer.getRole() != Role.NOT_CONNECTED ) {
			networkPlayer.stopEndpoint();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if( networkPlayer.getRole() != Role.NOT_CONNECTED ) {
			networkPlayer.closeEndpoint();
		}
	}
}
