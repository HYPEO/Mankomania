package space.hypeo.mankomania.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import space.hypeo.mankomania.actors.horse.HorseActor;

/**
 * Created by manuelegger on 11.06.18.
 */

public class HorseStageLogic {

    HorseActor horse1;
    HorseActor horse2;
    HorseActor horse3;
    HorseActor horse4;
    HorseActor winningHorse;

    float minTime;
    float maxTime;
    float avgQuote;
    Random rand;

    public HorseStageLogic() {
        horse1 = new HorseActor(1, "La Tartaruga", 1f);
        horse2 = new HorseActor(2, "Schnecki", 1.5f);
        horse3 = new HorseActor(3, "Salami", 1.8f);
        horse4 = new HorseActor(4, "Plumbum", 2.2f);

        minTime = 1.5f;
        maxTime = 3.5f; // note for adjustments: maxTime <= maxTime - minTime
        avgQuote = (horse1.getQuote() + horse2.getQuote() + horse3.getQuote() + horse4.getQuote()) / 4;
        rand = new Random();
    }

    public void calculateRaceTimes() {
        do {
            horse1.setRaceTime(((rand.nextFloat() * maxTime + minTime) * horse1.getQuote()) / avgQuote);
            horse2.setRaceTime(((rand.nextFloat() * maxTime + minTime) * horse1.getQuote()) / avgQuote);
            horse3.setRaceTime(((rand.nextFloat() * maxTime + minTime) * horse1.getQuote()) / avgQuote);
            horse4.setRaceTime(((rand.nextFloat() * maxTime + minTime) * horse1.getQuote()) / avgQuote);
        } while (!checkUniqueHorseTimes(horse1.getRaceTime(), horse2.getRaceTime(), horse3.getRaceTime(), horse4.getRaceTime()));

        winningHorse = calculateWinningHorse(horse1, horse2, horse3, horse4);
    }

    public boolean checkUniqueHorseTimes(float horse1, float horse2, float horse3, float horse4) {
        if(horse1 != horse2 && horse1 != horse3 && horse1 != horse4 && horse2 != horse3 &&
                horse2 != horse4 && horse3 != horse4) {
            return true;
        }
        else {
            return false;
        }
    }

    public HorseActor calculateWinningHorse(HorseActor horse1, HorseActor horse2, HorseActor horse3, HorseActor horse4) {
        List<HorseActor> competingHorses = new ArrayList<>();

        competingHorses.add(horse1);
        competingHorses.add(horse2);
        competingHorses.add(horse3);
        competingHorses.add(horse4);

        Collections.sort(competingHorses);

        return competingHorses.get(0);
    }
}
