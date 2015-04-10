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
import buka.wetten.EinsatzStrategie;
import buka.wetten.EinsatzStrategieKelly;
import buka.wetten.WettStrategie;
import buka.wetten.WettStrategieSchwarmintelligenzUndQuote;
import buka.wetten.Wette;
import buka.wetten.Zahlung;

public class BukaRow {

  private static final Zahlung BUDGET = Zahlung.DEFAULT_BUDGET_SPIELTAG;
  private final DecimalFormat dfDefault = new DecimalFormat("0");
  private final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
  private final Zahlung empfohlenerEinsatz;
  private final Ergebnis ergebnis;
  private final Wette favorisierteWette;
  private final Partie partie;
  private final Quote quote;
  private final TippStatistik tippAverage;
  private final TippStatistik tippLeader;

  public BukaRow(final Partie partie) {
    this.partie = partie;
    ergebnis = partie.getErgebnis();
    tippAverage = new TippBundesligaStatistikDeAverage(partie);
    tippLeader = new TippBundesligaStatistikDeLeader(partie);
    final QuotenFactory qf = new QuotenFactoryProxy(partie);
    final WettStrategie wettStrategie = new WettStrategieSchwarmintelligenzUndQuote((TippFactory) tippAverage, qf);
    favorisierteWette = wettStrategie.getFavorisierteWette();
    quote = qf.getQuote();
    final EinsatzStrategie einsatzStrategie = new EinsatzStrategieKelly(qf, wettStrategie);
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
    switch (favorisierteWette.getWetteAuf()) {
    case SIEG_AUSW:
      return "A";
    case SIEG_HEIM:
      return "H";
    case UNENTSCHIEDEN:
      return "U";
    default:
      return "NIX";
    }
  }

  public String getWetteEinsatz() {
    return empfohlenerEinsatz.toString();
  }

  public String getWetteGewinn() {
    Zahlung gewinn = favorisierteWette.getGewinn(partie, quote, empfohlenerEinsatz);
    if (gewinn == null) {
      return "?";
    } else {
      return gewinn.getEuroCents() == 0 ? "-" : gewinn.toString();
    }
  }

  public String getWetteVerlust() {
    Zahlung verlust = favorisierteWette.getVerlust(partie, quote, empfohlenerEinsatz);
    if (verlust == null) {
      return "?";
    } else {
      return verlust.getEuroCents() == 0 ? "-" : verlust.toString();
    }
  }

  public String getWetteWahrscheinlichkeit() {
    return Math.round(favorisierteWette.getWahrscheinlichkeit() * 100) + "";
  }
}