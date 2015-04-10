package buka.wetten;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import buka.quoten.Quote;

public class EinsatzStrategieKellyTest {

  @Test
  public void seekAndDestroyKasper150410() {
    Quote quote = new Quote();
    quote.setSiegHeim(1.4);
    quote.setUnentschieden(5D);
    quote.setSiegAusw(7D);
    Wette wette = new Wette(WetteAuf.SIEG_HEIM, .64);
    EinsatzStrategie es = new EinsatzStrategieKelly(quote, wette);
    Zahlung budget = es.getEmpfohlenenEinsatz(Zahlung.DEFAULT_BUDGET_SPIELTAG);
    int assertEinsatz = (int) Math.round(budget.getEuroCents() * ((1.4 * .64 - 1) / (1.4 - 1)));
    assertEquals(assertEinsatz, es.getEmpfohlenenEinsatz(budget).getEuroCents());
  }

  @Test
  public void testGetEmpfohlenenEinsatz() {
    Quote quote = new Quote();
    quote.setSiegAusw(1.5);
    Wette wette = new Wette(WetteAuf.SIEG_AUSW, .8);
    EinsatzStrategie es = new EinsatzStrategieKelly(quote, wette);
    Zahlung budget = es.getEmpfohlenenEinsatz(Zahlung.DEFAULT_BUDGET_PARTIE);
    int assertBudget = (int) Math.round(budget.getEuroCents() * ((1.5 * .8 - 1) / (1.5 - 1)));
    assertEquals(assertBudget, es.getEmpfohlenenEinsatz(budget).getEuroCents());
    assertEquals("1,78 €", es.getEmpfohlenenEinsatz(budget).toString());
  }
}
