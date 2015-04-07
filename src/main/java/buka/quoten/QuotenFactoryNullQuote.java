package buka.quoten;

import buka.modelLibsAndDips.Quote;
import buka.modelLibsAndDips.QuotenFactory;

class QuotenFactoryNullQuote implements QuotenFactory {

  @Override
  public Quote getQuote() {
    return new Quote();
  }
}
