package buka.openLigaDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import buka.modelLibsAndDips.Ergebnis;
import buka.modelLibsAndDips.Mannschaft;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Spieltag;
import buka.modelLibsAndDips.URLReader;

public class SpieltagOpenLigaDB implements Spieltag {

  private static int getCurrentNumber() {
    JSONObject current = URLReader.getJSONObjectFromUrl("http://www.openligadb.de/api/getcurrentgroup/bl1");
    return current.getInt("GroupOrderID");
  }

  private final int number;
  private List<Partie> partien = null;
  private final JSONArray partienJSON;

  public SpieltagOpenLigaDB() {
    this(getCurrentNumber());
  }

  public SpieltagOpenLigaDB(final int number) {
    this.number = number;
    if (number < 1 || number > 34) {
      System.err.println(number + " ist kein valider Spieltag");
      System.exit(1504061220);
    }
    String year = "2014"; // TODO manage saison requested
    String url = String.format("http://www.openligadb.de/api/getmatchdata/bl1/%s/%s", year, number);
    partienJSON = URLReader.getJSONArrayFromUrl(url);
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public List<Partie> getPartien() {
    if (partien != null) {
      return partien;
    }
    partien = new ArrayList<>(9);
    for (int i = 0; i < partienJSON.length(); i++) {
      JSONObject partieJSON = partienJSON.getJSONObject(i);
      JSONObject teamHeim = partieJSON.getJSONObject("Team1");
      JSONObject teamAusw = partieJSON.getJSONObject("Team2");
      Mannschaft mannschaftHeim = new Mannschaft(teamHeim.getString("TeamName"), teamHeim.getInt("TeamId"));
      Mannschaft mannschaftAusw = new Mannschaft(teamAusw.getString("TeamName"), teamAusw.getInt("TeamId"));
      // anpfiff
      String timestamp = partieJSON.getString("MatchDateTimeUTC");
      DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);
      Calendar anpfiff = Calendar.getInstance();
      anpfiff.setTime(date.toDate());
      // add with ergebnis (if finished)
      Partie partie = new Partie(this, mannschaftHeim, mannschaftAusw, partieJSON.getInt("MatchID"), anpfiff);
      if (partieJSON.getBoolean("MatchIsFinished")) {
        final JSONArray matchResults = partieJSON.getJSONArray("MatchResults");
        JSONObject matchResult = matchResults.getJSONObject(0);
        if (!matchResult.getString("ResultName").equals("Endergebnis")) {
          matchResult = matchResults.getJSONObject(1);
        }
        partie.setErgebnis(new Ergebnis(matchResult.getInt("PointsTeam1"), matchResult.getInt("PointsTeam2")));
      }
      partien.add(partie);
    }
    return partien;
  }
}
