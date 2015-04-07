package buka.modelLibsAndDips;

public class Partie {

  private final Mannschaft heim;
  private final Mannschaft ausw;
  private final int id;
  private final Spieltag spieltag;

  public Partie(Spieltag spieltag, Mannschaft heim, Mannschaft ausw, int id) {
    this.heim = heim;
    this.ausw = ausw;
    this.id = id;
    this.spieltag = spieltag;
  }

  public int getId() {
    return id;
  }

  public Spieltag getSpieltag() {
    return spieltag;
  }

  public Mannschaft getMannschaftHeim() {
    return heim;
  }

  public Mannschaft getMannschaftAusw() {
    return ausw;
  }
}
