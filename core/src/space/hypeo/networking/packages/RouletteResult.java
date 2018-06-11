package space.hypeo.networking.packages;

import space.hypeo.mankomania.player.PlayerSkeleton;

public class RouletteResult extends PlayerSkeleton {

    private int resultNo;

    /* NOTE: default constructor is required for network traffic */
    public RouletteResult() {
    }

    public RouletteResult(String nickname) {
        super(nickname);
    }

    public RouletteResult(PlayerSkeleton playerSkeleton) {
        super(playerSkeleton);
    }

    public int getResultNo() {
        return resultNo;
    }

    public void setResultNo(int resultNo) {
        this.resultNo = resultNo;
    }
}
