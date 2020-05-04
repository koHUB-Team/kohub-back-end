package kr.kohub.type;

public enum OrderOptionType {
  ASC("ASC"), DESC("DESC");

  private final String typeName;

  private OrderOptionType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
