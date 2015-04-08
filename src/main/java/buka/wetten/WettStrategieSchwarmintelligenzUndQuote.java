package buka.wetten;

import java.util.List;

import buka.quoten.Quote;
import buka.quoten.QuotenFactory;
import buka.tipps.TippFactory;
import buka.tipps.TippOfUser;

public class WettStrategieSchwarmintelligenzUndQuote implements WettStrategie {

  private final WettStrategie wettStrategie1;
  private final WettStrategie wettStrategie2;

  public WettStrategieSchwarmintelligenzUndQuote(final List<TippOfUser> tipps, final Quote quote) {
    wettStrategie1 = new WettStrategieSchwarmintelligenz(tipps);
    wettStrategie2 = new WettStrategieQuoteSafe(quote);
  }

  public WettStrategieSchwarmintelligenzUndQuote(final TippFactory tippFactory, final QuotenFactory quotenFactory) {
    this(tippFactory.getTippsOfUsers(), quotenFactory.getQuote());
  }

  @Override
  public Wette getFavorisierteWette() {
    Wette wette1 = wettStrategie1.getFavorisierteWette();
    Wette wette2 = wettStrategie2.getFavorisierteWette();
    if (wette1.isDoNotBetBet() || wette2.isDoNotBetBet() || wette1.getWetteAuf() != wette2.getWetteAuf()) {
      return Wette.LIEBER_NICHT;
    } else {
      double wahrscheinlichkeit = (wette1.getWahrscheinlichkeit() + wette2.getWahrscheinlichkeit()) / 2;
      Wette wette = new Wette(wette1.getWetteAuf(), wahrscheinlichkeit);
      return wette;
    }
  }
}
