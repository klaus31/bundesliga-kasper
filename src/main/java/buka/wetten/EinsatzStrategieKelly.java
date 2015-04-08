package buka.wetten;

import buka.quoten.Quote;
import buka.quoten.QuotenFactory;

public class EinsatzStrategieKelly implements EinsatzStrategie {

  private final Quote quote;
  private final Wette wette;

  public EinsatzStrategieKelly(final Quote quote, final Wette wette) {
    this.wette = wette;
    this.quote = quote;
  }

  public EinsatzStrategieKelly(final QuotenFactory quotenFactory, final WettStrategie wettStrategie) {
    this(quotenFactory.getQuote(), wettStrategie.getFavorisierteWette());
  }

  @Override
  public Budget getEmpfohlenenEinsatz(final Budget partieBudget) {
    // http://de.wikipedia.org/wiki/Kelly-Formel
    if (wette.isDoNotBetBet()) {
      return Budget.NO;
    }
    if (quote == null) {
      return Budget.NO;
    }
    final double w = wette.getWahrscheinlichkeit();
    final double q = quote.getProfitRate(wette);
    if (q < 1) {
      return Budget.NO;
    }
    final double k = (q * w - 1) / (q - 1);
    return new Budget(k * partieBudget.getEuroCents());
  }
}
