package buka.wetten;

import java.text.DecimalFormat;

public class Budget {

  public static final Budget DEFAULT_PARTIE = new Budget(10000 / 9);
  public static final Budget DEFAULT_SPIELTAG = new Budget(10000);
  public static final Budget NO = new Budget(0);;
  private final int cents;

  public Budget(final double d) {
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
