package space.hypeo.mankomania.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import space.hypeo.mankomania.actors.horse.HorseActor;

/**
 * Created by manuelegger on 11.06.18.
 */

public class HorseRaceStageLogic {

    // HorseActors
    HorseActor horse1;
    HorseActor horse2;
    HorseActor horse3;
    HorseActor horse4;
    HorseActor winningHorse;

    // MoveToActions
    private MoveToAction horse1Movement;
    private MoveToAction horse2Movement;
    private MoveToAction horse3Movement;
    private MoveToAction horse4Movement;

    // Race Times
    float minTime;
    float maxTime;
    float avgQuote;
    Random rand;

    // Stage Info
    float stageHeight;
    float stageWidth;

    // Bet Info
    int selectedHorse = 0;

    public HorseRaceStageLogic(float stageHeight, float stageWidth) {
        this.stageHeight = stageHeight;
        this.stageWidth = stageWidth;

        horse1 = new HorseActor(1, "La Tartaruga", 1f);
        horse2 = new HorseActor(2, "Schnecki", 1.5f);
        horse3 = new HorseActor(3, "Salami", 1.8f);
        horse4 = new HorseActor(4, "Plumbum", 2.2f);

        minTime = 1.5f;
        maxTime = 3.5f; // note for adjustments: maxTime <= maxTime - minTime
        avgQuote = (horse1.getQuote() + horse2.getQuote() + horse3.getQuote() + horse4.getQuote()) / 4;
        rand = new Random();

        calculateRaceTimes();
        calculateMovements();
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

    private void calculateMovements() {
        // Horse 1
        horse1Movement = new MoveToAction();
        horse1Movement.setPosition(stageWidth - horse1.getWidth(), stageHeight - horse1.getHeight());
        horse1Movement.setDuration(horse1.getRaceTime());
        horse1Movement.setInterpolation(Interpolation.fade);

        // Horse 2
        horse2Movement = new MoveToAction();
        horse2Movement.setPosition(stageWidth - horse2.getWidth(),stageHeight - horse2.getHeight() * 2);
        horse2Movement.setDuration(horse2.getRaceTime());
        horse2Movement.setInterpolation(Interpolation.fade);

        // Horse 3
        horse3Movement = new MoveToAction();
        horse3Movement.setPosition(stageWidth - horse2.getWidth(),stageHeight - horse3.getHeight() * 3);
        horse3Movement.setDuration(horse3.getRaceTime());
        horse3Movement.setInterpolation(Interpolation.fade);

        // Horse 4
        horse4Movement = new MoveToAction();
        horse4Movement.setPosition(stageWidth - horse2.getWidth(),stageHeight - horse4.getHeight() * 4);
        horse4Movement.setDuration(horse4.getRaceTime());
        horse4Movement.setInterpolation(Interpolation.fade);
    }

    public HorseActor getHorse1() {
        return horse1;
    }

    public HorseActor getHorse2() {
        return horse2;
    }

    public HorseActor getHorse3() {
        return horse3;
    }

    public HorseActor getHorse4() {
        return horse4;
    }

    public MoveToAction getHorse1Movement() {
        return horse1Movement;
    }

    public MoveToAction getHorse2Movement() {
        return horse2Movement;
    }

    public MoveToAction getHorse3Movement() {
        return horse3Movement;
    }

    public MoveToAction getHorse4Movement() {
        return horse4Movement;
    }

    public HorseActor getWinningHorse() {
        return winningHorse;
    }

    public int getSelectedHorse() {
        return selectedHorse;
    }

    public void setSelectedHorse(int selectedHorse) {
        this.selectedHorse = selectedHorse;
    }

    public float getSelectedHorseQuote() {
        switch (selectedHorse) {
            case 1: return horse1.getQuote();
            case 2: return horse2.getQuote();
            case 3: return horse3.getQuote();
            case 4: return horse4.getQuote();
        }
        return 0;
    }
}
