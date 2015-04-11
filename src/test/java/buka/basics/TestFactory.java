package buka.basics;

import buka.basics.Mannschaft;

public class TestFactory {

  public static Mannschaft getDortmund() {
    return new Mannschaft("Borussia Dortmund", 7);
  }

  public static Mannschaft getKöln() { // yeah, eat umlaute!
    return new Mannschaft("1. FC Köln", 65);
  }
}
