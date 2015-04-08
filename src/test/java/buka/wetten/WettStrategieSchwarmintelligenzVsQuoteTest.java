package buka.wetten;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buka.quoten.Quote;
import buka.tipps.TippOfUser;

public class WettStrategieSchwarmintelligenzVsQuoteTest {

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

  @Test
  public void testGetFavorisierteWette() {
    List<TippOfUser> tipps = new ArrayList<>();
    tipps.addAll(getTippOfUser(0, 1, 10));
    tipps.addAll(getTippOfUser(1, 1, 10));
    tipps.addAll(getTippOfUser(2, 1, 20));
    Quote quote = new Quote();
    quote.setSiegHeim(2D);
    WettStrategie ws = new WettStrategieSchwarmintelligenzVsQuote(tipps, quote);
    Wette wette = ws.getFavorisierteWette();
    assertEquals(WetteAuf.SIEG_HEIM, wette.getWetteAuf());
    assertEquals(.5, wette.getWahrscheinlichkeit(), .01);
  }
}
