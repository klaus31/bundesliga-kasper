package buka.wetten;

import java.security.InvalidParameterException;

import buka.basics.Partie;

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

  public Boolean won(final Partie partie) {
    if (partie.isFinished()) {
      return partie.getErgebnis().matches(getWetteAuf());
    } else {
      return null;
    }
  }
}
