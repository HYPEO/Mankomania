package space.hypeo.mankomania.game;

import com.badlogic.gdx.graphics.Texture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import space.hypeo.mankomania.GameTest;
import space.hypeo.mankomania.actors.horse.HorseActor;

import static org.junit.Assert.assertEquals;

/**
 * Created by manuelegger on 11.06.18.
 */

public class HorseRaceStageLogicTest extends GameTest {

    private HorseRaceStageLogic raceLogic;

    HorseActor horse1;
    HorseActor horse2;
    HorseActor horse3;
    HorseActor horse4;

    @Mock
    private Texture horseTexture;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        raceLogic = new HorseRaceStageLogic(800, 400, horseTexture);

        horse1 = new HorseActor(1, "La Tartaruga", 1f, horseTexture);
        horse2 = new HorseActor(2, "Schnecki", 1.5f, horseTexture);
        horse3 = new HorseActor(3, "Salami", 1.8f, horseTexture);
        horse4 = new HorseActor(4, "Plumbum", 2.2f, horseTexture);
    }

    @Test
    public void calculateWinningHorseTest() {
        horse1.setRaceTime(200);
        horse2.setRaceTime(100);
        horse3.setRaceTime(300);
        horse4.setRaceTime(400);

        HorseActor winningHorse = raceLogic.calculateWinningHorse(horse1, horse2, horse3, horse4);
        assertEquals(winningHorse.getId(), 2);

        horse1.setRaceTime(100);
        horse2.setRaceTime(400);
        horse3.setRaceTime(300);
        horse4.setRaceTime(200);

        winningHorse = raceLogic.calculateWinningHorse(horse1, horse2, horse3, horse4);
        assertEquals(winningHorse.getId(), 1);

        horse1.setRaceTime(400);
        horse2.setRaceTime(300);
        horse3.setRaceTime(100);
        horse4.setRaceTime(200);

        winningHorse = raceLogic.calculateWinningHorse(horse1, horse2, horse3, horse4);
        assertEquals(winningHorse.getId(), 3);

        horse1.setRaceTime(300);
        horse2.setRaceTime(200);
        horse3.setRaceTime(400);
        horse4.setRaceTime(100);

        winningHorse = raceLogic.calculateWinningHorse(horse1, horse2, horse3, horse4);
        assertEquals(winningHorse.getId(), 4);
    }

    @Test
    public void checkUniqueRaceTimesTest() {
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(1f, 2.3f, 2.3f, 4f));
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(1f, 2f, 3.999f, 3.999f));
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(1.1f, 2.3f, 2.3f, 1.1f));
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(1.1f, 1f, 1.1f, 4f));
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(2.3f, 2.3f, 2.3f, 2.3f));
        Assert.assertFalse(raceLogic.checkUniqueHorseTimes(1f, 2.3f, 2.3f, 2.3f));

        Assert.assertTrue(raceLogic.checkUniqueHorseTimes(1f, 2.3f, 3.3f, 4f));
    }

    @Test
    public void horseSelectionTest() {
        assertEquals(raceLogic.getSelectedHorseQuote(), 0f, 0f);

        raceLogic.setSelectedHorse(1);
        assertEquals(raceLogic.getSelectedHorseQuote(), 1f, 0f);

        raceLogic.setSelectedHorse(2);
        assertEquals(raceLogic.getSelectedHorseQuote(), 1.5f, 0f);

        raceLogic.setSelectedHorse(3);
        assertEquals(raceLogic.getSelectedHorseQuote(), 1.8f, 0f);

        raceLogic.setSelectedHorse(4);
        assertEquals(raceLogic.getSelectedHorseQuote(), 2f, 0f);

        assertEquals(raceLogic.getSelectedHorse(), 4);
    }

    @Test
    public void horseMovementMappingTest() {
        assertEquals(raceLogic.getHorse1().getRaceTime(),
                raceLogic.getHorse1Movement().getDuration(), 0f);

        assertEquals(raceLogic.getHorse2().getRaceTime(),
                raceLogic.getHorse2Movement().getDuration(), 0f);

        assertEquals(raceLogic.getHorse3().getRaceTime(),
                raceLogic.getHorse3Movement().getDuration(), 0f);

        assertEquals(raceLogic.getHorse4().getRaceTime(),
                raceLogic.getHorse4Movement().getDuration(), 0f);
    }
}
