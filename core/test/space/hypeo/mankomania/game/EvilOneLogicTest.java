package space.hypeo.mankomania.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import space.hypeo.mankomania.actors.player.PlayerActor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EvilOneLogicTest {
    private EvilOneLogic evilOne;
    private int dice1;
    private int dice2;
    private int sum;
    @Mock
    private PlayerActor playerActor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        evilOne = new EvilOneLogic(playerActor);
    }
    @After
    public void clean_up() {
        evilOne.setTotaldiceMoney(0);
        sum = 0;
    }

    @Test
    public void test_simpleDices()
    {
        dice1 = 2;
        dice2 = 2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals((dice1+dice2)*5000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 3;
        dice2 = 3;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals((dice1+dice2)*5000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 4;
        dice2 = 4;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals((dice1+dice2)*5000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 5;
        dice2 = 5;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals((dice1+dice2)*5000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 6;
        dice2 = 6;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals((dice1+dice2)*5000,evilOne.getTotalDiceMoney());
    }
    @Test
    public void test_failDices()
    {
        dice1 = 1;
        dice2 = 2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(-100000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 3;
        dice2 = 1;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(-100000,evilOne.getTotalDiceMoney());
        evilOne.setTotaldiceMoney(0);
        dice1 = 1;
        dice2 = 1;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(-300000,evilOne.getTotalDiceMoney());
    }
    @Test
    public void test_2winningInARow()
    {
        dice1 = 2;
        dice2 = 3;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 4;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(sum*5000,evilOne.getTotalDiceMoney());
        sum = 0;
        evilOne.setTotaldiceMoney(0);
        dice1 = 4;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 5;
        dice2 = 6;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(sum*5000,evilOne.getTotalDiceMoney());
    }
    @Test
    public void test_3winningInARow()
    {
        dice1 = 2;
        dice2 = 6;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 3;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 4;
        dice2 = 2;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(sum*5000,evilOne.getTotalDiceMoney());
    }
    public void test_LosingAfter2winningInARow()
    {
        dice1 = 2;
        dice2 = 3;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 4;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 1;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(-100000,evilOne.getTotalDiceMoney());
    }
    @Test
    public void test_LosingAfter3winningInARow()
    {
        dice1 = 2;
        dice2 = 6;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 3;
        dice2 = 5;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 4;
        dice2 = 2;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        dice1 = 1;
        dice2 = 1;
        sum = sum + dice1 + dice2;
        evilOne.checkDiceResults(dice1,dice2);
        assertEquals(-300000,evilOne.getTotalDiceMoney());
    }

}
