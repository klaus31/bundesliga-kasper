package buka;

import java.text.DecimalFormat;
import java.util.List;

import buka.bundesligaStatistikDe.TippBundesligaStatistikDeAverage;
import buka.bundesligaStatistikDe.TippBundesligaStatistikDeLeader;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Spieltag;
import buka.modelLibsAndDips.Tipp;
import buka.openLigaDB.SpieltagOpenLigaDB;

public class MainBukaOut {

  public static void main(final String... args) {
    final Spieltag spieltag = new SpieltagOpenLigaDB();
    final List<Partie> partien = spieltag.getPartien();
    final DecimalFormat df = new DecimalFormat("0.00");
    partien.forEach(partie -> {
      final Tipp tippAverage = new TippBundesligaStatistikDeAverage(partie);
      final Tipp tippLeader = new TippBundesligaStatistikDeLeader(partie);
      System.out.println("Partie: " + partie.getMannschaftHeim().getName() + " : " + partie.getMannschaftAusw().getName());
      System.out.println(tippAverage.getPerson() + ": " + df.format(tippAverage.getToreHeim()) + " : " + df.format(tippAverage.getToreAusw()));
      System.out.println(tippLeader.getPerson() + ": " + tippLeader.getToreHeim() + " : " + tippLeader.getToreAusw());
      System.out.println(System.lineSeparator());
    });
  }
}
