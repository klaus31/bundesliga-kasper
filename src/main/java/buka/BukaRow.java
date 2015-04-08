package buka;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import buka.bundesligaStatistikDe.TippBundesligaStatistikDeAverage;
import buka.bundesligaStatistikDe.TippBundesligaStatistikDeLeader;
import buka.modelLibsAndDips.Ergebnis;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Quote;
import buka.modelLibsAndDips.QuotenFactory;
import buka.modelLibsAndDips.Tipp;
import buka.quoten.QuotenFactoryProxy;

public class BukaRow {

  private final DecimalFormat dfDefault = new DecimalFormat("0");
  private final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
  private final Ergebnis ergebnis;
  private final Partie partie;
  private final Quote quote;
  private final Tipp tippAverage;
  private final Tipp tippLeader;

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