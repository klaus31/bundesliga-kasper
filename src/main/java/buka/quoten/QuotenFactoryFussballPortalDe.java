package buka.quoten;

import org.jsoup.Jsoup;

import buka.basics.Partie;
import buka.basics.URLReader;

class QuotenFactoryFussballPortalDe implements QuotenFactory {

  private static String content = null;
  private final boolean correctSpieltag;
  private final Partie partie;

  public QuotenFactoryFussballPortalDe(final Partie partie) {
    if (content == null) {
      final String url = "http://www.fussballportal.de/news/1-bundesliga/bundesliga-wettquoten";
      final String htmlContent = URLReader.getStringFromUrl(url);
      content = Jsoup.parse(htmlContent.replaceAll("(?i)<br[^>]*>", "br2n").replaceAll("(?i)<tr[^>]*>", "br2n")).text();
      content = content.replaceAll("br2n", System.lineSeparator());
    }
    this.partie = partie;
    correctSpieltag = content.indexOf("Spieltag " + partie.getSpieltag().getNumber()) > 0;
  }

  private String getMannschaftName(final String raw) {
    String result = raw;
    if (result.equals("Bayer 04 Leverkusen")) {
      result = "Bayer Leverkusen";
    } else if (result.equals("TSG 1899 Hoffenheim")) {
      result = "1899 Hoffenheim";
    } else if (result.equals("FC Schalke 04")) {
      result = "Schalke 04";
    } else if (result.equals("SC Paderborn 07")) {
      result = "SC Paderborn";
    } else if (result.equals("Borussia Mönchengladbach")) {
      result = "Mönchengladbach";
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

  public boolean isCorrectSpieltag() {
    return correctSpieltag;
  }
}
