package buka;

import java.text.DecimalFormat;

import buka.bundesligaStatistikDe.TippBundesligaStatistikDeAverage;
import buka.bundesligaStatistikDe.TippBundesligaStatistikDeLeader;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Tipp;

public class BukaRow {

  private final String partie;
  private final String tippAverage;
  private final String tippLeader;

  public BukaRow(final Partie partie) {
    final DecimalFormat dfAverage = new DecimalFormat("0.00");
    final DecimalFormat dfDefault = new DecimalFormat("0");
    final Tipp tippAverage = new TippBundesligaStatistikDeAverage(partie);
    final Tipp tippLeader = new TippBundesligaStatistikDeLeader(partie);
    this.tippAverage = new String(dfAverage.format(tippAverage.getToreHeim()) + " : " + dfAverage.format(tippAverage.getToreAusw()));
    this.tippLeader = new String(dfDefault.format(tippLeader.getToreHeim()) + " : " + dfDefault.format(tippLeader.getToreAusw()));
    this.partie = new String(partie.getMannschaftHeim().getName() + " : " + partie.getMannschaftAusw().getName());
  }

  public String getPartie() {
    return partie;
  }

  public String getTippAverage() {
    return tippAverage;
  }

  public String getTippLeader() {
    return tippLeader;
  }
}