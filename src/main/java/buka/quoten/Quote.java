package buka.quoten;

import buka.wetten.Wette;

public class Quote {

  private Double siegAusw = null;
  private Double siegHeim = null;
  private Double unentschieden = null;

  public Double getProfitRate(final Wette wette) {
    switch (wette.getWetteAuf()) {
    case SIEG_AUSW:
      return getSiegAusw();
    case SIEG_HEIM:
      return getSiegHeim();
    case UNENTSCHIEDEN:
      return getUnentschieden();
    default:
      return 0D;
    }
  }

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
    try {
      quote = quote.replaceAll(",", ".");
      return Double.parseDouble(quote);
    } catch (NumberFormatException e) {
      return null;
    }
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
