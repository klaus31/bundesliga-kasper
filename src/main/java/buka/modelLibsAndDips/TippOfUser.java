package buka.modelLibsAndDips;

public class TippOfUser implements Tipp {

  private String person;
  private Double toreAusw;
  private Double toreHeim;

  @Override
  public String getPerson() {
    return person;
  }

  @Override
  public Double getToreAusw() {
    return toreAusw;
  }

  @Override
  public Double getToreHeim() {
    return toreHeim;
  }

  public void setPerson(final String person) {
    this.person = person;
  }

  public void setToreAusw(final Double toreAusw) {
    this.toreAusw = toreAusw;
  }

  public void setToreAusw(final String tore) throws NumberFormatException {
    setToreAusw(Double.parseDouble(tore.trim()));
  }

  public void setToreHeim(final Double toreHeim) {
    this.toreHeim = toreHeim;
  }

  public void setToreHeim(final String tore) throws NumberFormatException {
    setToreHeim(Double.parseDouble(tore.trim()));
  }
}
