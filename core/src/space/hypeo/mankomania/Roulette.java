package space.hypeo.mankomania;

import space.hypeo.mankomania.actors.player.PlayerActor;

public class Roulette {
    private int numOfSpins;
    private PlayerActor playerActor;
    private String errorMessage;
    private String green;
    private String black;
    private String red;

    public Roulette(PlayerActor playerActor, int numOfSpins)
    {
        this.playerActor = playerActor;
        this.numOfSpins = numOfSpins;
        errorMessage = "Error occured";
        green = "green";
        black = "black";
        red = "red";
    }

    public String getResult(Integer money, String selectedColour) {
        //TODO: Change Player Balance
        String winningColour;
        int factor = 0;
        if (numOfSpins % 37 == 0) {
            winningColour = green;
            factor = 14;
        } else if ((numOfSpins % 2 == 1 && numOfSpins / 37 % 2 == 0) || (numOfSpins % 2 == 0 && numOfSpins / 37 % 2 == 1)) {
            winningColour = black;
            factor = 2;
        } else if ((numOfSpins % 2 == 0 && numOfSpins / 37 % 2 == 0) || (numOfSpins % 2 == 1 && numOfSpins / 37 % 2 == 1)) {
            winningColour = red;
            factor = 2;
        } else
            winningColour = errorMessage;
        if (winningColour.equals(selectedColour)) {
            //playerActor.changeBalance(money * factor);
            return "You Won";

        } else if (winningColour.equals(errorMessage))
            return errorMessage;
        else {
            //playerActor.changeBalance(money * (-1));
            return "You Lost";
        }
    }
}
