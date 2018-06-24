package space.hypeo.mankomania.game;

import org.junit.Before;
import org.junit.Test;

import space.hypeo.mankomania.GameTest;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by manuelegger on 14.06.18.
 */

public class SlotMachineLogicTest extends GameTest {
    private SlotMachineLogic slotMachineLogic;

    @Before
    public void setUp() {
        slotMachineLogic = new SlotMachineLogic();
    }

    @Test
    public void correctSymbolsIDsTest() {
        for (int i = 0; i <= 100; i++) {
            slotMachineLogic.calculateResult();
            assertTrue(slotMachineLogic.getSymbol1() >= 1 && slotMachineLogic.getSymbol1() <= 4);
            assertTrue(slotMachineLogic.getSymbol2() >= 1 && slotMachineLogic.getSymbol2() <= 4);
            assertTrue(slotMachineLogic.getSymbol3() >= 1 && slotMachineLogic.getSymbol3() <= 4);
        }
    }

    @Test
    public void grandPriceTest() {
        slotMachineLogic.setSymbol1(1);
        slotMachineLogic.setSymbol2(1);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getGrandPrice());
        assertTrue(slotMachineLogic.isGameWon());
        assertEquals(slotMachineLogic.getPriceType(), "Grand Price");
    }

    @Test
    public void bigPriceTest() {
        slotMachineLogic.setSymbol1(2);
        slotMachineLogic.setSymbol2(2);
        slotMachineLogic.setSymbol3(2);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getBigPrice());

        slotMachineLogic.setSymbol1(3);
        slotMachineLogic.setSymbol2(3);
        slotMachineLogic.setSymbol3(3);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getBigPrice());

        slotMachineLogic.setSymbol1(4);
        slotMachineLogic.setSymbol2(4);
        slotMachineLogic.setSymbol3(4);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getBigPrice());

        assertTrue(slotMachineLogic.isGameWon());
        assertEquals(slotMachineLogic.getPriceType(), "Big Price");
    }

    @Test
    public void smallPriceTest() {
        slotMachineLogic.setSymbol1(1);
        slotMachineLogic.setSymbol2(1);
        slotMachineLogic.setSymbol3(2);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getSmallPrice());

        slotMachineLogic.setSymbol1(2);
        slotMachineLogic.setSymbol2(1);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getSmallPrice());

        slotMachineLogic.setSymbol1(4);
        slotMachineLogic.setSymbol2(3);
        slotMachineLogic.setSymbol3(4);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getSmallPrice());

        slotMachineLogic.setSymbol1(1);
        slotMachineLogic.setSymbol2(3);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getSmallPrice());

        slotMachineLogic.setSymbol1(1);
        slotMachineLogic.setSymbol2(3);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), slotMachineLogic.getSmallPrice());

        assertTrue(slotMachineLogic.isGameWon());
        assertEquals(slotMachineLogic.getPriceType(), "Small Price");
    }

    @Test
    public void looseTest() {
        slotMachineLogic.setSymbol1(1);
        slotMachineLogic.setSymbol2(2);
        slotMachineLogic.setSymbol3(3);

        assertEquals(slotMachineLogic.getPrice(), 0);

        slotMachineLogic.setSymbol1(4);
        slotMachineLogic.setSymbol2(2);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), 0);

        slotMachineLogic.setSymbol1(3);
        slotMachineLogic.setSymbol2(4);
        slotMachineLogic.setSymbol3(1);

        assertEquals(slotMachineLogic.getPrice(), 0);

        assertFalse(slotMachineLogic.isGameWon());
        assertEquals(slotMachineLogic.getPriceType(), "");
    }
}
