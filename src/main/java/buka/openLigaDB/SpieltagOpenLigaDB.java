package buka.openLigaDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import buka.modelLibsAndDips.Mannschaft;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Spieltag;
import buka.modelLibsAndDips.URLReader;

public class SpieltagOpenLigaDB implements Spieltag {

  private static int getCurrentNumber() {
    JSONObject current = URLReader.getJSONObjectFromUrl("http://www.openligadb.de/api/getcurrentgroup/bl1");
    return current.getInt("GroupOrderID") + 1;
  }

  private final int number;
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
    List<Partie> result = new ArrayList<>(9);
    for (int i = 0; i < partienJSON.length(); i++) {
      JSONObject partie = partienJSON.getJSONObject(i);
      JSONObject team1 = partie.getJSONObject("Team1");
      JSONObject team2 = partie.getJSONObject("Team2");
      Mannschaft mannschaft1 = new Mannschaft(team1.getString("TeamName"), team1.getInt("TeamId"));
      Mannschaft mannschaft2 = new Mannschaft(team2.getString("TeamName"), team2.getInt("TeamId"));
      // anpfiff
      String timestamp = partie.getString("MatchDateTimeUTC");
      DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);
      Calendar anpfiff = Calendar.getInstance();
      anpfiff.setTime(date.toDate());
      result.add(new Partie(this, mannschaft1, mannschaft2, partie.getInt("MatchID"), anpfiff));
    }
    return result;
  }
}
