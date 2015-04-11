package buka.basics;

import java.util.Calendar;
import java.util.Date;

import buka.spieltage.Spieltag;

public class Partie {

  private final Calendar anpfiff;
  private final Mannschaft ausw;
  private Ergebnis ergebnis = null;
  private final Mannschaft heim;
  private final int id;
  private Spieltag spieltag = null;

  public Partie(final Mannschaft heim, final Mannschaft ausw, final int id, final Calendar anpfiff) {
    this.heim = heim;
    this.ausw = ausw;
    this.id = id;
    this.anpfiff = anpfiff;
  }

  public Calendar getAbpfiff() {
    Calendar abpfiff = (Calendar) anpfiff.clone();
    abpfiff.add(Calendar.MINUTE, 120);
    return abpfiff;
  }

  public Calendar getAnpfiff() {
    return anpfiff;
  }

  public Ergebnis getErgebnis() {
    return ergebnis;
  }

  public int getId() {
    return id;
  }

  public Mannschaft getMannschaftAusw() {
    return ausw;
  }

  public Mannschaft getMannschaftHeim() {
    return heim;
  }

  public Spieltag getSpieltag() {
    return spieltag;
  }

  public boolean hasErgebnis() {
    return ergebnis != null;
  }

  public boolean is(final Mannschaft heim, final Mannschaft ausw) {
    return this.heim.getId() == heim.getId() && this.ausw.getId() == ausw.getId();
  }

  public boolean isBetween(final Mannschaft team1, final Mannschaft team2) {
    return is(team1, team2) || this.heim.getId() == team2.getId() && this.ausw.getId() == team1.getId();
  }

  public boolean isFinished() {
    return getAbpfiff().getTime().before(new Date());
  }

  public void setErgebnis(final Ergebnis ergebnis) {
    this.ergebnis = ergebnis;
  }

  public void setSpieltag(final Spieltag spieltag) {
    this.spieltag = spieltag;
  }
}
