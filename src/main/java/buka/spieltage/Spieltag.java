package buka.spieltage;

import java.util.List;

import buka.basics.Partie;

public interface Spieltag {

  List<Partie> getPartien();

  int getNumber();
}
