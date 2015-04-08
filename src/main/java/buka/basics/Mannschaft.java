package buka.basics;

public class Mannschaft {

  private final String name;
  private final int id;

  public Mannschaft(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
