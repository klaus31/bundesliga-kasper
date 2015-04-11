package buka.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import buka.basics.Mannschaft;
import buka.basics.OpenLigaDB;
import buka.basics.Partie;

public class ErgebnisFactoryDefault implements ErgebnisFactory {

  private final static int OLDEST_YEAR = 2002;
  private final Map<Integer, JSONArray> ergebnisse = new HashMap<>();

  private JSONArray getJSON(final int year) {
    if (year < OLDEST_YEAR) {
      return null;
    }
    if (!ergebnisse.containsKey(year)) {
      try {
        String source = IOUtils.toString(ErgebnisFactoryDefault.class.getResourceAsStream(year + ".json"));
        JSONArray ergebnisseOfYear = new JSONArray(source);
        ergebnisse.put(year, ergebnisseOfYear);
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(150411);
      }
    }
    return ergebnisse.get(year);
  }

  @Override
  public Partie getLastFinishedPartie(final Mannschaft heim, final Mannschaft ausw) {
    List<Partie> partien = getLastFinishedPartien(1, heim, ausw);
    if (partien == null || partien.isEmpty()) {
      return null;
    } else {
      return partien.get(0);
    }
  }

  @Override
  public Partie getLastFinishedPartieBetween(final Mannschaft heim, final Mannschaft ausw) {
    List<Partie> partien = getLastFinishedPartienBetween(1, heim, ausw);
    if (partien == null || partien.isEmpty()) {
      return null;
    } else {
      return partien.get(0);
    }
  }

  /* TODO hier muss so etwas rein wie "ausgehend vom datum". andernfalls
   * betrachtest du irgendwann mal die statistik von 2012 und es werden spiele
   * von 2017 beachtet. */
  @Override
  public List<Partie> getLastFinishedPartien(final int count, final Mannschaft heim, final Mannschaft ausw) {
    List<Partie> result = new ArrayList<>();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    while (result.size() < count) {
      List<Partie> partien = getPartienOf(currentYear--);
      if (partien == null) {
        break;
      }
      partien.forEach(partie -> {
        if (partie.is(heim, ausw) && partie.hasErgebnis() && result.size() < count) {
          result.add(partie);
        }
      });
    }
    return result;
  }

  /* TODO hier muss so etwas rein wie "ausgehend vom datum". andernfalls
   * betrachtest du irgendwann mal die statistik von 2012 und es werden spiele
   * von 2017 beachtet. */
  @Override
  public List<Partie> getLastFinishedPartienBetween(final int count, final Mannschaft team1, final Mannschaft team2) {
    List<Partie> result = new ArrayList<>();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    while (result.size() < count) {
      List<Partie> partien = getPartienOf(currentYear--);
      if (partien == null) {
        break;
      }
      partien.forEach(partie -> {
        if (partie.isBetween(team1, team2) && partie.hasErgebnis() && result.size() < count) {
          result.add(partie);
        }
      });
    }
    return result;
  }

  private List<Partie> getPartienOf(final int currentYear) {
    JSONArray json = getJSON(currentYear);
    if (json == null) {
      return null;
    }
    final List<Partie> result = new ArrayList<>();
    int index = json.length();
    while (index-- > 0) {
      JSONObject partieJSON = json.getJSONObject(index);
      Partie partie = OpenLigaDB.getPartie(partieJSON);
      result.add(partie);
    }
    return result;
  }
}
