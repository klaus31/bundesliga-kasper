package buka.wetten;

import java.text.DecimalFormat;

public class Zahlung {

  public static final Zahlung DEFAULT_BUDGET_PARTIE = new Zahlung(10000 / 9);
  public static final Zahlung DEFAULT_BUDGET_SPIELTAG = new Zahlung(10000);
  public static final Zahlung NIX = new Zahlung(0);
  private final int cents;

  public Zahlung(final double d) {
    this.cents = (int) Math.round(d);
  }

  public int getEuroCents() {
    return cents;
  }

  public double getEuros() {
    return 1D * cents / 100;
  }

  @Override
  public String toString() {
    final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
    return dfTwoDp.format(this.getEuros()) + " €";
  }
}
