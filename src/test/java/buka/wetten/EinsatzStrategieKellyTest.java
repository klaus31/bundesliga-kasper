package buka.wetten;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import buka.quoten.Quote;

public class EinsatzStrategieKellyTest {

  @Test
  public void testGetEmpfohlenenEinsatz() {
    Quote quote = new Quote();
    quote.setSiegAusw(1.5);
    Wette wette = new Wette(WetteAuf.SIEG_AUSW, .8);
    EinsatzStrategie es = new EinsatzStrategieKelly(quote, wette);
    Budget budget = es.getEmpfohlenenEinsatz(Budget.DEFAULT_PARTIE);
    int assertBudget = (int) Math.round(budget.getEuroCents() * ((1.5 * .8 - 1) / (1.5 - 1)));
    assertEquals(assertBudget, es.getEmpfohlenenEinsatz(budget).getEuroCents());
  }
}
