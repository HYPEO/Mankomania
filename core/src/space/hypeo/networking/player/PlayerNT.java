package space.hypeo.networking.player;

import com.esotericsoftware.minlog.Log;

import java.net.InetAddress;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.endpoint.IClientConnector;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.mankomania.IDeviceStateSubscriber;
import space.hypeo.mankomania.player.IPlayerConnector;
import space.hypeo.networking.endpoint.IHostConnector;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.network.Role;

/**
 * This class is a wrapper class for an endpoint.
 * An endpoint can be server or client.
 * The class represents the network connection of the current player and
 * communicates with other endpoints.
 */
public class PlayerNT implements IPlayerConnector, IDeviceStateSubscriber {
    private final PlayerManager playerManager;
    private final IEndpoint endpoint;

    public PlayerNT(final PlayerManager playerManager, final IEndpoint endpoint) {
        this.playerManager = playerManager;
        this.endpoint = endpoint;
    }

    public IEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public void endTurn() {
        // TODO: end the current turn for this player
    }

    @Override
    public void onPause() {
        endpoint.stop();
    }

    @Override
    public void onStop() {
        endpoint.stop();
    }

    @Override
    public void broadCastLobby() {
        endpoint.broadCastLobby();
    }

    public void kickPlayerFromLobby(PlayerSkeleton playerToKick) {
        if(playerManager.getRole() == Role.HOST) {
            Log.info("PlayerNT: Try to kick player " + playerToKick);
            IHostConnector host = (IHostConnector) endpoint;
            host.sendOrderToCloseConnection(playerToKick);
        }
    }

    public void startGame() {
        if(playerManager.getRole() == Role.HOST) {
            Log.info("PlayerNT: Start the Game");
            IHostConnector host = (IHostConnector) endpoint;
            host.startGame();
        }
    }

    @Override
    public void sendHorseRaceResult(String horseName) {
        endpoint.sendHorseRaceResult(horseName);
    }

    @Override
    public void sendRouletteResult(int slotId) {
        endpoint.sendRouletteResult(slotId);
    }

    /**
     * Connects the client to the host.
     * @param hostAddr IP address of host
     */
    public void connectToHost(InetAddress hostAddr) {
        Role role = playerManager.getRole();

        if(role == Role.CLIENT) {
            IClientConnector client = (IClientConnector) endpoint;
            client.connectToHost(hostAddr);
            Log.info(role + ": PlayerNT: Connect to host " + hostAddr);

            playerManager.showLobbyStage();

        } else {
            Log.info(role + ": PLayerNT: Can NOT connect to myself!");
        }
    }
}
