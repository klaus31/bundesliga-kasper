package buka.results;

import java.util.List;

import org.junit.Test;

import buka.basics.Mannschaft;
import buka.basics.Partie;
import buka.basics.TestFactory;
import buka.statistics.ErgebnisFactory;
import buka.statistics.ErgebnisFactoryDefault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ErgebnisFactoryTest {

  @Test
  public void getLast1000Partien() {
    Mannschaft heim = TestFactory.getDortmund();
    Mannschaft ausw = TestFactory.getKöln();
    ErgebnisFactory factory = new ErgebnisFactoryDefault();
    List<Partie> ergebnisse = factory.getLastFinishedPartien(1000, heim, ausw);
    assertTrue(ergebnisse.size() > 4);
  }

  @Test
  public void getLast3Partien() {
    Mannschaft heim = TestFactory.getDortmund();
    Mannschaft ausw = TestFactory.getKöln();
    ErgebnisFactory factory = new ErgebnisFactoryDefault();
    List<Partie> partien = factory.getLastFinishedPartien(3, heim, ausw);
    assertEquals(3, partien.size());
  }
}
