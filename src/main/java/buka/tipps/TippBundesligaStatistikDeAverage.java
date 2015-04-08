package buka.tipps;

import java.util.List;

import buka.basics.Partie;

public class TippBundesligaStatistikDeAverage extends TippBundesligaStatistikDe {

  public TippBundesligaStatistikDeAverage(final Partie partie) {
    super(partie);
  }

  @Override
  public String getName() {
    return "ø of " + this.getTippsOfUsers().size() + " users";
  }

  @Override
  public Double getToreAusw() {
    List<TippOfUser> tipps = this.getTippsOfUsers();
    double sum = 0;
    for (TippOfUser tipp : tipps) {
      sum += tipp.getToreAusw();
    }
    return 1D * sum / tipps.size();
  }

  @Override
  public Double getToreHeim() {
    List<TippOfUser> tipps = this.getTippsOfUsers();
    double sum = 0;
    for (TippOfUser tipp : tipps) {
      sum += tipp.getToreHeim();
    }
    return sum / tipps.size();
  }
}
