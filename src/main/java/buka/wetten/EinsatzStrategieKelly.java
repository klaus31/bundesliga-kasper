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

  /* FIXME das ganze zeugs hat leider ein design problem: Alles wird immer
   * anhand einer einzelnen partie berechnet. das budget allerdings ist für den
   * ganzen spieltag. so lässt sich nix berechnen auf "spieltag == 100 €"
   * du kannst also hier nur einen einsatz empfehlen, wenn du alle wetten und
   * quoten des gesamten spieltags hast (herzlichen glückwunsch dazu). */
  @Override
  public Zahlung getEmpfohlenenEinsatz(final Zahlung partieBudget) {
    // http://de.wikipedia.org/wiki/Kelly-Formel
    if (wette.isDoNotBetBet()) {
      return Zahlung.NIX;
    }
    if (quote == null) {
      return Zahlung.NIX;
    }
    final double w = wette.getWahrscheinlichkeit();
    final double q = quote.getProfitRate(wette);
    if (q < 1) {
      return Zahlung.NIX;
    }
    final double k = (q * w - 1) / (q - 1);
    return new Zahlung(k * partieBudget.getEuroCents());
  }
}
