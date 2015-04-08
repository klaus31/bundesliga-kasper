package buka.wetten;

import java.util.List;

import buka.tipps.TippFactory;
import buka.tipps.TippOfUser;

public class WettStrategieSchwarmintelligenz implements WettStrategie {

  private static final int ANZAHL_TIPPS_ERFORDERLICH = 10;
  private final List<TippOfUser> tipps;

  public WettStrategieSchwarmintelligenz(final List<TippOfUser> tipps) {
    this.tipps = tipps;
  }

  public WettStrategieSchwarmintelligenz(final TippFactory tippFactory) {
    this(tippFactory.getTippsOfUsers());
  }

  @Override
  public Wette getFavorisierteWette() {
    if (tipps.size() <= ANZAHL_TIPPS_ERFORDERLICH) {
      return Wette.LIEBER_NICHT;
    }
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
}
