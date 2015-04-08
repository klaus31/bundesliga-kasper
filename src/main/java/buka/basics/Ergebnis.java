package buka.basics;

import buka.wetten.WetteAuf;

public class Ergebnis {

  private Integer ausw = null;
  private Integer heim = null;

  public Ergebnis(final int heim, final int ausw) {
    this.heim = heim;
    this.ausw = ausw;
  }

  public int getAusw() {
    return ausw;
  }

  public int getHeim() {
    return heim;
  }

  public boolean matches(final WetteAuf wetteAuf) {
    return (heim > ausw && wetteAuf == WetteAuf.SIEG_HEIM) || (ausw > heim && wetteAuf == WetteAuf.SIEG_AUSW) || (ausw == heim && wetteAuf == WetteAuf.UNENTSCHIEDEN);
  }

  public void setErgebnis(final int heim, final int ausw) {
    this.heim = heim;
    this.ausw = ausw;
  }
}
