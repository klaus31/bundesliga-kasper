package buka.quoten;


class QuotenFactoryNullQuote implements QuotenFactory {

  @Override
  public Quote getQuote() {
    return new Quote();
  }
}
