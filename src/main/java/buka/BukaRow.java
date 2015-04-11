package buka;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import buka.basics.Ergebnis;
import buka.basics.Partie;
import buka.quoten.Quote;
import buka.quoten.QuotenFactory;
import buka.quoten.QuotenFactoryProxy;
import buka.statistics.ErgebnisFactory;
import buka.statistics.ErgebnisFactoryDefault;
import buka.tipps.TippBundesligaStatistikDeAverage;
import buka.tipps.TippBundesligaStatistikDeLeader;
import buka.tipps.TippStatistik;

public class BukaRow {

  public static final int HISTORIE_PARTIEN_BETRACHTET = 4;
  private final DecimalFormat dfDefault = new DecimalFormat("0");
  private final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
  private final ErgebnisFactory ef = new ErgebnisFactoryDefault();
  private final Ergebnis ergebnis;
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
    quote = qf.getQuote();
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

  /**
   * die letzten 5 partien dieser konstelation (heim auswärts oder umgekehrt)
   */
  public String getHistorieAll() {
    List<Partie> partien = ef.getLastFinishedPartienBetween(HISTORIE_PARTIEN_BETRACHTET, partie.getMannschaftHeim(), partie.getMannschaftAusw());
    if (partien.isEmpty()) {
      return "-";
    }
    int toreCurrentHeimMannschaft = 0;
    int toreCurrentAuswMannschaft = 0;
    for (Partie partie : partien) {
      if (partie.getMannschaftHeim().getId() == this.partie.getMannschaftHeim().getId()) {
        toreCurrentHeimMannschaft += partie.getErgebnis().getHeim();
        toreCurrentAuswMannschaft += partie.getErgebnis().getAusw();
      } else {
        toreCurrentHeimMannschaft += partie.getErgebnis().getAusw();
        toreCurrentAuswMannschaft += partie.getErgebnis().getHeim();
      }
    }
    final double toreHeimAvg = 1D * toreCurrentHeimMannschaft / partien.size();
    final double toreAuswAvg = 1D * toreCurrentAuswMannschaft / partien.size();
    return dfTwoDp.format(toreHeimAvg) + " : " + dfTwoDp.format(toreAuswAvg);
  }

  /**
   * die letzten 5 partien dieser konstelation (heim auswärts beachtet)
   */
  public String getHistorieHA() {
    List<Partie> partien = ef.getLastFinishedPartien(HISTORIE_PARTIEN_BETRACHTET, partie.getMannschaftHeim(), partie.getMannschaftAusw());
    if (partien.isEmpty()) {
      return "-";
    }
    int toreHeimMannschaft = 0;
    int toreAuswMannschaft = 0;
    for (Partie partie : partien) {
      toreHeimMannschaft += partie.getErgebnis().getHeim();
      toreAuswMannschaft += partie.getErgebnis().getAusw();
    }
    final double toreHeimAvg = 1D * toreHeimMannschaft / partien.size();
    final double toreAuswAvg = 1D * toreAuswMannschaft / partien.size();
    return dfTwoDp.format(toreHeimAvg) + " : " + dfTwoDp.format(toreAuswAvg);
  }

  public String getHistorieLetzteEgal() {
    Partie lastPartie = ef.getLastFinishedPartieBetween(this.partie.getMannschaftHeim(), this.partie.getMannschaftAusw());
    if (lastPartie == null) {
      return "-";
    } else {
      if (lastPartie.getMannschaftHeim().getId() == this.partie.getMannschaftHeim().getId()) {
        return lastPartie.getErgebnis().getHeim() + ":" + lastPartie.getErgebnis().getAusw();
      } else {
        return lastPartie.getErgebnis().getAusw() + ":" + lastPartie.getErgebnis().getHeim();
      }
    }
  }

  public String getHistorieLetzteHA() {
    Partie lastPartie = ef.getLastFinishedPartie(partie.getMannschaftHeim(), partie.getMannschaftAusw());
    if (lastPartie == null) {
      return "-";
    } else {
      return lastPartie.getErgebnis().getHeim() + ":" + lastPartie.getErgebnis().getAusw();
    }
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
}