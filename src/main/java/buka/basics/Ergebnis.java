package buka.basics;

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

  public void setErgebnis(final int heim, final int ausw) {
    this.heim = heim;
    this.ausw = ausw;
  }
}
