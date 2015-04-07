package buka.modelLibsAndDips;

public class TippOfUser implements Tipp {

  private String person;
  private double toreAusw;
  private double toreHeim;

  @Override
  public String getPerson() {
    return person;
  }

  @Override
  public double getToreAusw() {
    return toreAusw;
  }

  @Override
  public double getToreHeim() {
    return toreHeim;
  }

  public void setPerson(final String person) {
    this.person = person;
  }

  public void setToreAusw(final double toreAusw) {
    this.toreAusw = toreAusw;
  }

  public void setToreAusw(final String tore) throws NumberFormatException {
    setToreAusw(Integer.parseInt(tore.trim()));
  }

  public void setToreHeim(final double toreHeim) {
    this.toreHeim = toreHeim;
  }

  public void setToreHeim(final String tore) throws NumberFormatException {
    setToreHeim(Integer.parseInt(tore.trim()));
  }
}
