package buka.quoten;

import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Quote;
import buka.modelLibsAndDips.QuotenFactory;

public class QuotenFactoryProxy implements QuotenFactory {

  private final QuotenFactory quotenFactory;

  public QuotenFactoryProxy(final Partie partie) {
    if (partie.isFinished()) {
      quotenFactory = new QuotenFactoryTipico(partie);
    } else {
      QuotenFactoryFussballPortalDe qf = new QuotenFactoryFussballPortalDe(partie);
      if (qf.isCorrectSpieltag()) {
        quotenFactory = qf;
      } else {
        quotenFactory = new QuotenFactoryNullQuote();
      }
    }
  }

  @Override
  public Quote getQuote() {
    return quotenFactory.getQuote();
  }
}
