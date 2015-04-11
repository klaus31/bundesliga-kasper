package buka.statistics;

import java.util.List;

import buka.basics.Mannschaft;
import buka.basics.Partie;

public interface ErgebnisFactory {

  Partie getLastFinishedPartie(Mannschaft mannschaftHeim, Mannschaft mannschaftAusw);

  Partie getLastFinishedPartieBetween(Mannschaft mannschaftHeim, Mannschaft mannschaftAusw);

  /**
   * return the last <code>count</code> matches where heim mannschaft vs. ausw mannschaft @ home
   */
  List<Partie> getLastFinishedPartien(int count, Mannschaft heim, Mannschaft ausw);

  /**
   * return partien bei denen die beiden mannschaften gegeneinander gespielt haben (egal wo)
   */
  List<Partie> getLastFinishedPartienBetween(int i, Mannschaft team1, Mannschaft team2);
}
