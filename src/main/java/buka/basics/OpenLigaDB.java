package buka.basics;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenLigaDB {

  public static Partie getPartie(final JSONObject partieJSON) {
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
    Partie partie = new Partie(mannschaftHeim, mannschaftAusw, partieJSON.getInt("MatchID"), anpfiff);
    if (partieJSON.getBoolean("MatchIsFinished")) {
      final JSONArray matchResults = partieJSON.getJSONArray("MatchResults");
      if (matchResults.length() == 2) {
        JSONObject matchResult = matchResults.getJSONObject(0);
        if (!matchResult.getString("ResultName").equals("Endergebnis")) {
          matchResult = matchResults.getJSONObject(1);
        }
        partie.setErgebnis(new Ergebnis(matchResult.getInt("PointsTeam1"), matchResult.getInt("PointsTeam2")));
      }
    }
    return partie;
  }
}
