package buka.tipps;

public class TippOfUser implements Tipp {

  private String person;
  private int toreAusw;
  private int toreHeim;

  @Override
  public String getPerson() {
    return person;
  }

  @Override
  public int getToreAusw() {
    return toreAusw;
  }

  @Override
  public int getToreHeim() {
    return toreHeim;
  }

  public void setPerson(final String person) {
    this.person = person;
  }

  public void setToreAusw(final int toreAusw) {
    this.toreAusw = toreAusw;
  }

  public void setToreAusw(final String tore) throws NumberFormatException {
    setToreAusw(Integer.parseInt(tore.trim()));
  }

  public void setToreHeim(final int toreHeim) {
    this.toreHeim = toreHeim;
  }

  public void setToreHeim(final String tore) throws NumberFormatException {
    setToreHeim(Integer.parseInt(tore.trim()));
  }
}
