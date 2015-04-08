package buka;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import buka.basics.Ergebnis;
import buka.basics.Partie;
import buka.quoten.Quote;
import buka.quoten.QuotenFactory;
import buka.quoten.QuotenFactoryProxy;
import buka.tipps.TippBundesligaStatistikDeAverage;
import buka.tipps.TippBundesligaStatistikDeLeader;
import buka.tipps.TippFactory;
import buka.tipps.TippStatistik;
import buka.wetten.Budget;
import buka.wetten.EinsatzStrategie;
import buka.wetten.EinsatzStrategieKelly;
import buka.wetten.WettStrategie;
import buka.wetten.WettStrategieSchwarmintelligenzUndQuote;
import buka.wetten.Wette;
import buka.wetten.WetteAuf;

public class BukaRow {

  private static final Budget BUDGET = Budget.DEFAULT_SPIELTAG;
  private final DecimalFormat dfDefault = new DecimalFormat("0");
  private final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
  private final Budget empfohlenerEinsatz;
  private final Ergebnis ergebnis;
  private final Partie partie;
  private final Quote quote;
  private final TippStatistik tippAverage;
  private final TippStatistik tippLeader;
  private final WettStrategie wettStrategie;

  public BukaRow(final Partie partie) {
    this.partie = partie;
    ergebnis = partie.getErgebnis();
    tippAverage = new TippBundesligaStatistikDeAverage(partie);
    tippLeader = new TippBundesligaStatistikDeLeader(partie);
    final QuotenFactory qf = new QuotenFactoryProxy(partie);
    wettStrategie = new WettStrategieSchwarmintelligenzUndQuote((TippFactory) tippAverage, qf);
    quote = qf.getQuote();
    EinsatzStrategie einsatzStrategie = new EinsatzStrategieKelly(qf, wettStrategie);
    empfohlenerEinsatz = einsatzStrategie.getEmpfohlenenEinsatz(BUDGET);
  }

  public String getAnpfiff() {
    return new SimpleDateFormat("d.M.yy H:mm").format(partie.getAnpfiff().getTime());
  }

  public String getErgebnisAusw() {
    final Integer ergebnisAusw = ergebnis == null ? null : ergebnis.getAusw();
    return ergebnisAusw == null ? "?" : ergebnisAusw + "";
  }

  public String getErgebnisHeim() {
    final Integer ergebnisHeim = ergebnis == null ? null : ergebnis.getHeim();
    return ergebnisHeim == null ? "?" : ergebnisHeim + "";
  }

  public String getPartieAusw() {
    return partie.getMannschaftAusw().getName();
  }

  public String getPartieHeim() {
    return partie.getMannschaftHeim().getName();
  }

  public String getQuoteSiegAusw() {
    return quote.getSiegAusw() == null ? "?" : dfTwoDp.format(quote.getSiegAusw());
  }

  public String getQuoteSiegHeim() {
    return quote.getSiegHeim() == null ? "?" : dfTwoDp.format(quote.getSiegHeim());
  }

  public String getQuoteUnentschieden() {
    return quote.getUnentschieden() == null ? "?" : dfTwoDp.format(quote.getUnentschieden());
  }

  public String getTippAverageAusw() {
    String result = "?";
    if (tippAverage.getToreAusw() != null) {
      result = dfTwoDp.format(tippAverage.getToreAusw());
    }
    return result;
  }

  public String getTippAverageHeim() {
    String result = "?";
    if (tippAverage.getToreHeim() != null) {
      result = dfTwoDp.format(tippAverage.getToreHeim());
    }
    return result;
  }

  public String getTippLeaderAusw() {
    String result = "?";
    if (tippLeader.getToreAusw() != null) {
      result = dfDefault.format(tippLeader.getToreAusw());
    }
    return result;
  }

  public String getTippLeaderHeim() {
    String result = "?";
    if (tippLeader.getToreHeim() != null) {
      result = dfDefault.format(tippLeader.getToreHeim());
    }
    return result;
  }

  public String getWetteAuf() {
    switch (wettStrategie.getFavorisierteWette().getWetteAuf()) {
    case SIEG_AUSW:
      return "A";
    case SIEG_HEIM:
      return "H";
    case UNENTSCHIEDEN:
      return "U";
    default:
      return "!";
    }
  }

  public String getWetteEinsatz() {
    return empfohlenerEinsatz.toString();
  }

  public String getWetteGewinn() {
    if (partie.isFinished()) {
      Wette wette = wettStrategie.getFavorisierteWette();
      WetteAuf wetteAuf = wette.getWetteAuf();
      if (partie.getErgebnis().matches(wetteAuf)) {
        return new Budget(quote.getProfitRate(wette) * empfohlenerEinsatz.getEuroCents()).toString();
      } else {
        return Budget.NO.toString();
      }
    } else {
      return "?";
    }
  }

  public String getWetteVerlust() {
    if (partie.isFinished()) {
      Wette wette = wettStrategie.getFavorisierteWette();
      WetteAuf wetteAuf = wette.getWetteAuf();
      if (partie.getErgebnis().matches(wetteAuf)) {
        return Budget.NO.toString();
      } else {
        return new Budget(quote.getProfitRate(wette) * empfohlenerEinsatz.getEuroCents()).toString();
      }
    } else {
      return "?";
    }
  }

  public String getWetteWahrscheinlichkeit() {
    return Math.round(wettStrategie.getFavorisierteWette().getWahrscheinlichkeit() * 100) + "";
  }
}