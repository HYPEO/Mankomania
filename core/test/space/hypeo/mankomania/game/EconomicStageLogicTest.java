package space.hypeo.mankomania.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.GameTest;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EconomicStageLogicTest extends GameTest {


    private static final int BALANCE = 100000;
    private static final String testName="money";

    @Mock
    private EconomicStageLogic eco;
    @Mock
    private Image actorImage;
    @Mock
    private FieldActor fieldActor;
    @Mock
    private PlayerDetailActor playerDetailActor;
    @Mock
    private GameStateManager gameStateManager;
    @Mock
    private PlayerActor playerActor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        playerActor = new PlayerActor(actorImage, BALANCE, playerDetailActor, gameStateManager, "", "");
        playerActor.initializeState(fieldActor);
        eco = new EconomicStageLogic(playerActor, "test");

    }

    @Test
    public void getBalance() {
        // VERIFICATION:
        // Check if balance has been set correctly.
        assertEquals(BALANCE, playerActor.getBalance());
    }


    @Test
    public void payMoney() {
        // VERIFICATION:
        // Check if Player can pay.
        int currentBalance = playerActor.getBalance();
        int money = 500;
        eco.payMoney(500);
        int afterBalance = playerActor.getBalance();
        assertEquals(currentBalance - money, afterBalance);
    }

    @Test
    public void payMoneyNotUnderZero() {
        // VERIFICATION:
        // Check if Playermoney cannot go under zero.
        int money = playerActor.getBalance() + 1;
        eco.payMoney(money);
        int shouldBeZero = playerActor.getBalance();
        assertEquals(0, shouldBeZero);

    }

    @Test
    public void getMoney() {
        // VERIFICATION:
        // Check if Playermoney can get Money
        int currentBalance = playerActor.getBalance();
        int money = 500;
        eco.getMoney(500);
        int afterBalance = playerActor.getBalance();
        assertEquals(currentBalance + money, afterBalance);

    }

    @Test
    public void setPrefs() {
        // VERIFICATION:
        // Check if Preference can store integers.
        eco.setPrefs(testName,10);

        Preferences pref = Gdx.app.getPreferences("test");

        assertTrue(pref.contains(testName));
        assertEquals(10, pref.getInteger(testName));


    }
    @Test
    public void getPref() {
        // VERIFICATION:
        // Check if Preference get the correct int.
        eco.setPrefs(testName,10);
        int money = eco.getPrefs(testName);
        assertEquals(10, money);
    }

    @Test
    public void getPrefZero() {
        // VERIFICATION:
        // Check if Preference get Zero if Key is not created.
        int money = eco.getPrefs(testName+"1");
        assertEquals(0, money);
    }




}
