package buka;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import buka.bundesligaStatistikDe.TippBundesligaStatistikDeAverage;
import buka.bundesligaStatistikDe.TippBundesligaStatistikDeLeader;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Quote;
import buka.modelLibsAndDips.QuotenFactory;
import buka.modelLibsAndDips.Tipp;
import buka.quoten.QuotenFactoryProxy;

public class BukaRow {

  private final String anpfiff;
  private final DecimalFormat dfDefault = new DecimalFormat("0");
  private final DecimalFormat dfTwoDp = new DecimalFormat("0.00");
  private final String partieAusw;
  private final String partieHeim;
  private final String quoteSiegAusw;
  private final String quoteSiegHeim;
  private final String quoteUnentschieden;
  private final String tippAverageAusw;
  private final String tippAverageHeim;
  private final String tippLeaderAusw;
  private final String tippLeaderHeim;

  public BukaRow(final Partie partie) {
    final Tipp tippAverage = new TippBundesligaStatistikDeAverage(partie);
    final Tipp tippLeader = new TippBundesligaStatistikDeLeader(partie);
    final QuotenFactory qf = new QuotenFactoryProxy(partie);
    final Quote quote = qf.getQuote();
    this.tippAverageHeim = dfTwoDp.format(tippAverage.getToreHeim());
    this.tippAverageAusw = dfTwoDp.format(tippAverage.getToreAusw());
    this.tippLeaderHeim = dfDefault.format(tippLeader.getToreHeim());
    this.tippLeaderAusw = dfDefault.format(tippLeader.getToreAusw());
    this.partieHeim = partie.getMannschaftHeim().getName();
    this.partieAusw = partie.getMannschaftAusw().getName();
    this.quoteUnentschieden = formatQuoteValue(quote.getUnentschieden());
    this.quoteSiegHeim = formatQuoteValue(quote.getSiegHeim());
    this.quoteSiegAusw = formatQuoteValue(quote.getSiegAusw());
    this.anpfiff = new SimpleDateFormat("d.M.yy H:mm").format(partie.getAnpfiff().getTime());
  }

  private String formatQuoteValue(final Double quoteValue) {
    return quoteValue == null ? "?" : dfTwoDp.format(quoteValue);
  }

  public String getAnpfiff() {
    return anpfiff;
  }

  public String getPartieAusw() {
    return partieAusw;
  }

  public String getPartieHeim() {
    return partieHeim;
  }

  public String getQuoteSiegAusw() {
    return quoteSiegAusw;
  }

  public String getQuoteSiegHeim() {
    return quoteSiegHeim;
  }

  public String getQuoteUnentschieden() {
    return quoteUnentschieden;
  }

  public String getTippAverageAusw() {
    return tippAverageAusw;
  }

  public String getTippAverageHeim() {
    return tippAverageHeim;
  }

  public String getTippLeaderAusw() {
    return tippLeaderAusw;
  }

  public String getTippLeaderHeim() {
    return tippLeaderHeim;
  }
}