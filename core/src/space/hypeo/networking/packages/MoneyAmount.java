package space.hypeo.networking.packages;

public class MoneyAmount {

    private double moneyAmount;

    public MoneyAmount() {
        this(0);
    }

    public MoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    @Override
    public String toString() {
        return new Double(moneyAmount).toString();
    }
}
