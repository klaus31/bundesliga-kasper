package buka.wetten;

import java.security.InvalidParameterException;

import buka.basics.Partie;
import buka.quoten.Quote;

public class Wette {

  public static final Wette LIEBER_NICHT = new Wette(WetteAuf.LIEBER_GAR_NICHT, 0);
  private final double wahrscheinlichkeit;
  private final WetteAuf wetteAuf;

  public Wette(final WetteAuf wetteAuf, final double wahrscheinlichkeit) {
    if (wahrscheinlichkeit < 0 || wahrscheinlichkeit > 1) {
      throw new InvalidParameterException("wahrscheinlichkeit ist keine prozentangabe");
    }
    this.wahrscheinlichkeit = wahrscheinlichkeit;
    this.wetteAuf = wetteAuf;
  }

  public Zahlung getGewinn(final Partie partie, final Quote quote, final Zahlung einsatz) {
    final Boolean won = this.won(partie);
    if (won == null) {
      return null;
    } else {
      if (won) {
        double gewinn = quote.getProfitRate(this) * einsatz.getEuroCents() - einsatz.getEuroCents();
        return new Zahlung(gewinn);
      } else {
        return Zahlung.NIX;
      }
    }
  }

  public Zahlung getVerlust(final Partie partie, final Quote quote, final Zahlung einsatz) {
    final Boolean won = this.won(partie);
    if (won == null) {
      return null;
    } else {
      return won ? Zahlung.NIX : einsatz;
    }
  }

  /**
   * return percentage (0 <= percentage <= 100) where 0 is "hands off - do not bet"
   * and 100 stands for "no doubt - you already win".
   */
  public double getWahrscheinlichkeit() {
    return wahrscheinlichkeit;
  }

  public WetteAuf getWetteAuf() {
    return wetteAuf;
  }

  public boolean isDoNotBetBet() {
    return this.wetteAuf.equals(WetteAuf.LIEBER_GAR_NICHT);
  }

  private Boolean won(final Partie partie) {
    if (partie.isFinished()) {
      return partie.getErgebnis().matches(getWetteAuf());
    } else {
      return null;
    }
  }
}
