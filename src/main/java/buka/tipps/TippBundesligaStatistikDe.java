package buka.tipps;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import buka.basics.Mannschaft;
import buka.basics.Partie;
import buka.basics.URLReader;

public abstract class TippBundesligaStatistikDe implements TippFactory, TippStatistik {

  private static String content = null;

  static String getContent() {
    return content;
  }

  private final Partie partie;
  private List<TippOfUser> tippsOfUsers = null;

  public TippBundesligaStatistikDe(final Partie partie) {
    this.partie = partie;
    if (content == null) {
      final String url = "http://www.bundesliga-statistik.de/index.php?pos=tips_show&spieltag=" + partie.getSpieltag().getNumber();
      final String htmlContent = URLReader.getStringFromUrl(url, Charset.forName("ISO-8859-1"));
      content = Jsoup.parse(htmlContent.replaceAll("(?i)<br[^>]*>", "br2n").replaceAll("(?i)<tr[^>]*>", "br2n")).text();
      content = content.replaceAll("br2n", System.lineSeparator());
    }
  }

  protected String getName(final Mannschaft mannschaft) {
    String result = mannschaft.getName();
    if (result.equals("TSG 1899 Hoffenheim")) {
      result = "TSG Hoffenheim";
    } else if (result.equals("1. FSV Mainz 05")) {
      result = "FSV Mainz 05";
    } else if (result.equals("Hertha BSC")) {
      result = "Hertha BSC Berlin";
    } else if (result.equals("Borussia Mönchengladbach")) {
      result = "ladbach";
    }
    return result;
  }

  Partie getPartie() {
    return partie;
  }

  @Override
  public List<TippOfUser> getTippsOfUsers() {
    if (tippsOfUsers == null) {
      tippsOfUsers = new ArrayList<>();
      String result = getContent();
      final int index = result.indexOf(this.getName(this.getPartie().getMannschaftHeim()));
      if (result.length() > index) {
        result = result.substring(index);
        final String[] tippLines = result.split(System.lineSeparator());
        for (int i = 0; i < tippLines.length; i++) {
          if (i == 0) {
            continue;
          }
          String[] pieces = tippLines[i].trim().split(":");
          try {
            final TippOfUser tipp = new TippOfUser();
            tipp.setPerson(pieces[0].trim());
            tipp.setToreHeim(pieces[1].replaceAll(" ", "").replaceAll(" ", "").charAt(0) + "");
            tipp.setToreAusw(pieces[2].replaceAll(" ", "").replaceAll(" ", "").charAt(0) + "");
            tippsOfUsers.add(tipp);
          } catch (final NumberFormatException | IndexOutOfBoundsException e) {
            break;
          }
        }
      }
    }
    return tippsOfUsers;
  }
}
