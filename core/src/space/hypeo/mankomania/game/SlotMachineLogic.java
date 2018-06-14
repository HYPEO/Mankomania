package space.hypeo.mankomania.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by manuelegger on 14.06.18.
 */

public class SlotMachineLogic {
    static final int GRAND_PRICE = 250000;
    static final int BIG_PRICE = 150000;
    static final int SMALL_PRICE = 50000;

    int symbol1;
    int symbol2;
    int symbol3;
    ArrayList<Integer> results;

    Random random;

    public SlotMachineLogic() {
        results = new ArrayList<>();
        random = new Random();

        calculateResult();
    }

    public void calculateResult() {
        symbol1 = random.nextInt(4) + 1;
        symbol2 = random.nextInt(4) + 1;
        symbol3 = random.nextInt(4) + 1;
    }

    public int getPrice() {
        results.clear();
        results.add(symbol1);
        results.add(symbol2);
        results.add(symbol3);

        Collections.sort(results);

        // all 3 symbols are $
        if (results.get(2) == 1) {
            return GRAND_PRICE;
        }

        // all 3 symbols are the same
        if (results.get(0) == results.get(1) && results.get(1) == results.get(2)) {
            return BIG_PRICE;
        }

        // 2 symbols are the same
        if (results.get(0) == results.get(1) || results.get(1) == results.get(2)) {
            return SMALL_PRICE;
        }

        return 0;
    }

    public int getSymbol1() {
        return symbol1;
    }

    public void setSymbol1(int symbol1) {
        this.symbol1 = symbol1;
    }

    public int getSymbol2() {
        return symbol2;
    }

    public void setSymbol2(int symbol2) {
        this.symbol2 = symbol2;
    }

    public int getSymbol3() {
        return symbol3;
    }

    public void setSymbol3(int symbol3) {
        this.symbol3 = symbol3;
    }

    public static int getGrandPrice() {
        return GRAND_PRICE;
    }

    public static int getBigPrice() {
        return BIG_PRICE;
    }

    public static int getSmallPrice() {
        return SMALL_PRICE;
    }
}
