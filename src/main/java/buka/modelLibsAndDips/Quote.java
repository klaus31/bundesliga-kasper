package buka.modelLibsAndDips;

public class Quote {

  private Double siegAusw = null;
  private Double siegHeim = null;
  private Double unentschieden = null;

  public Double getSiegAusw() {
    return siegAusw;
  }

  public Double getSiegHeim() {
    return siegHeim;
  }

  public Double getUnentschieden() {
    return unentschieden;
  }

  private Double parseDouble(String quote) {
    if (quote.equals("-")) {
      return null;
    }
    quote = quote.replaceAll(",", ".");
    return Double.parseDouble(quote);
  }

  public void setSiegAusw(final Double siegAusw) {
    this.siegAusw = siegAusw;
  }

  public void setSiegAusw(final String quote) {
    setSiegAusw(parseDouble(quote));
  }

  public void setSiegHeim(final Double siegHeim) {
    this.siegHeim = siegHeim;
  }

  public void setSiegHeim(final String quote) {
    setSiegHeim(parseDouble(quote));
  }

  public void setUnentschieden(final Double unentschieden) {
    this.unentschieden = unentschieden;
  }

  public void setUnentschieden(final String quote) {
    setUnentschieden(parseDouble(quote));
  }
}
