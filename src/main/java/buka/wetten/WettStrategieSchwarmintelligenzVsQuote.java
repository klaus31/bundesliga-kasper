package buka.wetten;

import java.util.List;

import buka.quoten.Quote;
import buka.quoten.QuotenFactory;
import buka.tipps.TippFactory;
import buka.tipps.TippOfUser;

public class WettStrategieSchwarmintelligenzVsQuote implements WettStrategie {

  private static final int ANZAHL_TIPPS_ERFORDERLICH = 10;
  private final Quote quote;
  private final List<TippOfUser> tipps;

  public WettStrategieSchwarmintelligenzVsQuote(final List<TippOfUser> tipps, final Quote quote) {
    this.tipps = tipps;
    this.quote = quote;
  }

  public WettStrategieSchwarmintelligenzVsQuote(final TippFactory tippFactory, final QuotenFactory quotenFactory) {
    this(tippFactory.getTippsOfUsers(), quotenFactory.getQuote());
  }

  @Override
  public Wette getFavorisierteWette() {
    if (quote == null || tipps.size() <= ANZAHL_TIPPS_ERFORDERLICH) {
      return Wette.LIEBER_NICHT;
    }
    Wette schwarmWette = getSchwarmWette();
    return schwarmWette;
  }

  @Override
  public Integer getGewinn() {
    return null;
  }

  private Wette getSchwarmWette() {
    // count tipps
    int countSiegHeim = 0;
    int countUnentschieden = 0;
    int countSiegAusw = 0;
    for (TippOfUser tipp : tipps) {
      if (tipp.getToreHeim() == tipp.getToreAusw()) {
        countUnentschieden++;
      } else if (tipp.getToreAusw() > tipp.getToreHeim()) {
        countSiegAusw++;
      } else {
        countSiegHeim++;
      }
    }
    // set WetteAuf
    WetteAuf wetteAuf = WetteAuf.LIEBER_GAR_NICHT;
    double wahrscheinlichkeit = 0;
    if (countUnentschieden > countSiegAusw && countUnentschieden > countSiegHeim) {
      wetteAuf = WetteAuf.UNENTSCHIEDEN;
      wahrscheinlichkeit = 1D * countUnentschieden / tipps.size();
    } else if (countSiegAusw > countSiegHeim && countSiegAusw > countUnentschieden) {
      wetteAuf = WetteAuf.SIEG_AUSW;
      wahrscheinlichkeit = 1D * countSiegAusw / tipps.size();
    } else if (countSiegHeim > countSiegAusw && countSiegHeim > countUnentschieden) {
      wetteAuf = WetteAuf.SIEG_HEIM;
      wahrscheinlichkeit = 1D * countSiegHeim / tipps.size();
    }
    return new Wette(wetteAuf, wahrscheinlichkeit);
  }

  @Override
  public Integer getVerlust() {
    return null;
  }
}
