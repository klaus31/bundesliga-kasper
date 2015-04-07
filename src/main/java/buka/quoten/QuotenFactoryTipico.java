package buka.quoten;

import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;

import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Quote;
import buka.modelLibsAndDips.QuotenFactory;
import buka.modelLibsAndDips.URLReader;

class QuotenFactoryTipico implements QuotenFactory {

  private static String content = null;
  private final Partie partie;

  public QuotenFactoryTipico(final Partie partie) {
    this.partie = partie;
    Calendar anpfiff = partie.getAnpfiff();
    if (anpfiff.after(new Date())) {
      System.err.println("This class does only work with past anpfiffies");
      System.exit(1504071003);
    }
    if (content == null) {
      final String urlFormat = "https://www.tipico.de/de/ergebnisse/fussball/deutschland/g42301-1-bundesliga/%s/kw%s/";
      String url = String.format(urlFormat, anpfiff.get(Calendar.YEAR), anpfiff.get(Calendar.WEEK_OF_YEAR));
      final String htmlContent = URLReader.getStringFromUrl(url);
      content = Jsoup.parse(htmlContent.replaceAll("(?i)<br[^>]*>", "br2n").replaceAll("(?i)<tr[^>]*>", "br2n")).text();
      content = content.replaceAll("br2n", System.lineSeparator());
    }
  }

  private String getMannschaftName(final String raw) {
    String result = raw;
    if (result.equals("Bayer 04 Leverkusen")) {
      result = "Bayer Leverkusen";
    } else if (result.equals("TSG 1899 Hoffenheim")) {
      result = "1899 Hoffenheim";
    } else if (result.equals("1. FSV Mainz 05")) {
      result = "FSV Mainz 05";
    } else if (result.equals("1. FC Köln")) {
      result = "1.FC Köln";
    } else if (result.equals("Borussia Dortmund")) {
      result = "Bor. Dortmund";
    } else if (result.equals("Bayern München")) {
      result = "Bay. München";
    } else if (result.equals("FC Schalke 04")) {
      result = "Schalke 04";
    } else if (result.equals("SC Paderborn 07")) {
      result = "SC Paderborn";
    } else if (result.equals("Borussia Mönchengladbach")) {
      // TODO mal Borussia M'gladbach und mal Bor. M'gladbach
      result = "M'gladbach";
    }
    return result;
  }

  @Override
  public Quote getQuote() {
    final String heim = getMannschaftName(this.partie.getMannschaftHeim().getName());
    final String ausw = getMannschaftName(this.partie.getMannschaftAusw().getName());
    String part = content.substring(content.indexOf(heim));
    part = part.substring(part.indexOf(ausw) + ausw.length());
    part = part.substring(0, 20).trim();
    String[] quotenString = part.split(" ");
    final Quote result = new Quote();
    result.setSiegHeim(quotenString[0]);
    result.setUnentschieden(quotenString[1]);
    result.setSiegAusw(quotenString[2]);
    return result;
  }
}
