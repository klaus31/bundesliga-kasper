package buka.wetten;

import buka.quoten.Quote;

/**
 * ermittel den mit der geringsten gewinn-marge (das wahrscheinlichste ergebnis)
 *
 */
public class WettStrategieQuoteSafe implements WettStrategie {

  private final Quote quote;

  public WettStrategieQuoteSafe(final Quote quote) {
    this.quote = quote;
  }

  @Override
  public Wette getFavorisierteWette() {
    if (quote == null || !quote.isComplete()) {
      return Wette.LIEBER_NICHT;
    }
    if (quote.getSiegAusw() < quote.getSiegHeim() && quote.getSiegAusw() < quote.getUnentschieden()) {
      return new Wette(WetteAuf.SIEG_AUSW, 1 / quote.getSiegAusw());
    } else if (quote.getSiegHeim() < quote.getSiegAusw() && quote.getSiegHeim() < quote.getUnentschieden()) {
      return new Wette(WetteAuf.SIEG_HEIM, 1 / quote.getSiegHeim());
    } else if (quote.getUnentschieden() < quote.getSiegHeim() && quote.getUnentschieden() < quote.getSiegAusw()) {
      return new Wette(WetteAuf.UNENTSCHIEDEN, 1 / quote.getUnentschieden());
    } else {
      return Wette.LIEBER_NICHT;
    }
  }
}
