package buka.quoten;

import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;

import buka.basics.Partie;
import buka.basics.URLReader;

class QuotenFactoryTipico implements QuotenFactory {

  private static String currentContent = null;
  private static String currentUrl = null;
  private final Partie partie;

  public QuotenFactoryTipico(final Partie partie) {
    this.partie = partie;
    Calendar anpfiff = partie.getAnpfiff();
    if (anpfiff.after(new Date())) {
      System.err.println("This class does only work with past anpfiffies");
      System.exit(1504071003);
    }
    final String urlFormat = "https://www.tipico.de/de/ergebnisse/fussball/deutschland/g42301-1-bundesliga/%s/kw%s/";
    String url = String.format(urlFormat, anpfiff.get(Calendar.YEAR), anpfiff.get(Calendar.WEEK_OF_YEAR));
    if (currentContent == null || !url.equals(currentUrl)) {
      currentUrl = url;
      final String htmlContent = URLReader.getStringFromUrl(url);
      currentContent = Jsoup.parse(htmlContent.replaceAll("(?i)<br[^>]*>", "br2n").replaceAll("(?i)<tr[^>]*>", "br2n")).text();
      currentContent = currentContent.replaceAll("br2n", System.lineSeparator());
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
      // TODO mal Bor. Dortmund mal Borussia Dortmund
      result = "Dortmund";
    } else if (result.equals("Bayern München")) {
      // TODO mal Bay. München mal Bayern München
      result = "München";
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

  // FIXME englische woche geht net
  @Override
  public Quote getQuote() {
    final String heim = getMannschaftName(this.partie.getMannschaftHeim().getName());
    final String ausw = getMannschaftName(this.partie.getMannschaftAusw().getName());
    try {
      String part = currentContent.substring(currentContent.indexOf(heim));
      part = part.substring(part.indexOf(ausw) + ausw.length());
      part = part.substring(0, 20).trim();
      String[] quotenString = part.split(" ");
      final Quote result = new Quote();
      result.setSiegHeim(quotenString[0]);
      result.setUnentschieden(quotenString[1]);
      result.setSiegAusw(quotenString[2]);
      return result;
    } catch (StringIndexOutOfBoundsException e) {
      e.printStackTrace();
      System.err.println(heim + " : " + ausw);
      return new Quote();
    }
  }
}
