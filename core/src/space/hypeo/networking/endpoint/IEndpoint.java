package space.hypeo.networking.endpoint;

import space.hypeo.networking.network.PlayerBusiness;
import space.hypeo.networking.network.PlayerSkeleton;
import space.hypeo.networking.packages.Remittances;


/**
 * This class provides functionality for an endpoint of a connection.
 */
public interface IEndpoint {

    public void stop();

    public void close();

    /**
     * Update view of game lobby.
     */
    //public void updateStageLobby() {
        /*
        Log.info(role + ": updateStageLobby");

        Stage currentStage = stageManager.getCurrentStage();

        if (currentStage instanceof LobbyStage) {
            ((LobbyStage) currentStage).updateLobby();
        }
        */
    //}

    /**
     * Toggles the status of a player in the lobby between:
     * ready2play <-> not_ready2play
     *
     * @param player2toggleReadyStatus name of the player
     */
    public void toggleReadyStatus(PlayerSkeleton player2toggleReadyStatus);

    /**
     * Resends a received MoneyAmount from player to another player.
     *
     * @param remittances
     */
    public void changeBalance(Remittances remittances);
}
