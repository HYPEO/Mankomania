package space.hypeo.mankomania;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import space.hypeo.networking.network.NetworkPlayer;
import space.hypeo.networking.network.Role;

public class AndroidLauncher extends AndroidApplication {

	Mankomania mankomania = null;

	private NetworkPlayer player = null;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		mankomania = new Mankomania();
		initialize(mankomania, config);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if( player != null && player.getRole() != Role.NOT_CONNECTED ) {
			player.stopEndpoint();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if( player != null && player.getRole() != Role.NOT_CONNECTED ) {
			player.closeEndpoint();
		}
	}
}
