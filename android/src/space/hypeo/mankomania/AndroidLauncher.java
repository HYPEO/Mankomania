package space.hypeo.mankomania;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import space.hypeo.networking.network.WhatAmI;

public class AndroidLauncher extends AndroidApplication {

	Mankomania mankomania = null;

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
		WhatAmI.stopEndpoint();
	}

	@Override
	protected void onStop() {
		super.onStop();
		WhatAmI.closeEndpoint();
	}
}
