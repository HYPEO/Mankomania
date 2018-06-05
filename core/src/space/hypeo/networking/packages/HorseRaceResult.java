package space.hypeo.networking.packages;

import space.hypeo.mankomania.player.PlayerSkeleton;

public class HorseRaceResult extends PlayerSkeleton {

    private String horseName;

    /* NOTE: default constructor is required for network traffic */
    public HorseRaceResult() {
    }

    public HorseRaceResult(String nickname) {
        super(nickname);
    }

    public HorseRaceResult(PlayerSkeleton p) {
        super(p);
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public String getHorseName() {
        return horseName;
    }
}
