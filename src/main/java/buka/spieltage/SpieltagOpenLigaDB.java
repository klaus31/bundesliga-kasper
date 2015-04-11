package buka.spieltage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import buka.basics.OpenLigaDB;
import buka.basics.Partie;
import buka.basics.URLReader;

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
      Partie partie = OpenLigaDB.getPartie(partieJSON);
      partie.setSpieltag(this);
      partien.add(partie);
    }
    return partien;
  }
}
