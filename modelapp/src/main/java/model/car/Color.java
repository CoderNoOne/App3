package model.car;

public enum Color {
  BLACK("czarny"),
  BLUE("niebieski"),
  GOLD("złoy"),
  GREEN("zielony"),
  ORANGE("pomarańczowy"),
  PURPLE("fioletowy"),
  RED("czerwony"),
  SILVER("srebrny"),
  WHITE("biały"),
  YELLOW("żółty");

  private String polishDescription;

  Color(String polishDescription) {
    this.polishDescription = polishDescription;
  }

  public String getPolishDescription() {
    return polishDescription;
  }
}
