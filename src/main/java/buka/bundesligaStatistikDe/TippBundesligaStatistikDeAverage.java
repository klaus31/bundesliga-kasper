package buka.bundesligaStatistikDe;

import java.util.List;

import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.TippOfUser;

public class TippBundesligaStatistikDeAverage extends TippBundesligaStatistikDe {

  public TippBundesligaStatistikDeAverage(final Partie partie) {
    super(partie);
  }

  @Override
  public String getPerson() {
    return "ø of " + this.getTippsOfUsers().size() + " users";
  }

  @Override
  public Double getToreAusw() {
    List<TippOfUser> tipps = this.getTippsOfUsers();
    double sum = 0;
    for (TippOfUser tipp : tipps) {
      sum += tipp.getToreAusw();
    }
    return sum / tipps.size();
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
