package buka.wetten;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buka.quoten.Quote;
import buka.tipps.TippOfUser;

public class WettStrategieSchwarmintelligenzUndQuoteTest {

  private List<TippOfUser> getTippOfUser(final int toreHeim, final int toreAusw, final int anzahl) {
    List<TippOfUser> result = new ArrayList<>();
    int i = 0;
    while (i++ < anzahl) {
      TippOfUser tou = new TippOfUser();
      tou.setToreAusw(toreAusw);
      tou.setToreHeim(toreHeim);
      result.add(tou);
    }
    return result;
  }

  /* hier versuche ich trottel das problem zu beheben, das einsätze negative
   * vorzeichen tragen.
   * dabei weiß ich schon, dass es bei englischen wochen der fall ist - es gibt
   * auch eine index out of bounds exception. die quote zeigt immer die spiele
   * einer kw und der parser ist - schlecht. */
  @Test
  public void seekAndDestroyKasper150410() {
    List<TippOfUser> tipps = new ArrayList<>();
    tipps.addAll(getTippOfUser(2, 1, 10));
    tipps.addAll(getTippOfUser(1, 1, 8));
    tipps.addAll(getTippOfUser(2, 2, 1));
    Quote quote = new Quote();
    quote.setSiegHeim(1.4);
    quote.setUnentschieden(5D);
    quote.setSiegAusw(7D);
    WettStrategie ws = new WettStrategieSchwarmintelligenzUndQuote(tipps, quote);
    Wette wette = ws.getFavorisierteWette();
    assertEquals(WetteAuf.SIEG_HEIM, wette.getWetteAuf());
    assertEquals(.62, wette.getWahrscheinlichkeit(), .01);
    EinsatzStrategie es = new EinsatzStrategieKelly(quote, wette);
    Zahlung budget = es.getEmpfohlenenEinsatz(Zahlung.DEFAULT_BUDGET_SPIELTAG);
    assertEquals(87, es.getEmpfohlenenEinsatz(budget).getEuroCents());
  }

  @Test
  public void testGetFavorisierteWette() {
    List<TippOfUser> tipps = new ArrayList<>();
    tipps.addAll(getTippOfUser(0, 1, 10));
    tipps.addAll(getTippOfUser(1, 1, 10));
    tipps.addAll(getTippOfUser(2, 1, 20));
    Quote quote = new Quote();
    quote.setSiegHeim(1.5);
    quote.setSiegAusw(2D);
    quote.setUnentschieden(2D);
    WettStrategie ws = new WettStrategieSchwarmintelligenzUndQuote(tipps, quote);
    Wette wette = ws.getFavorisierteWette();
    assertEquals(WetteAuf.SIEG_HEIM, wette.getWetteAuf());
    assertEquals((.5 + .666) / 2, wette.getWahrscheinlichkeit(), .01);
  }
}
