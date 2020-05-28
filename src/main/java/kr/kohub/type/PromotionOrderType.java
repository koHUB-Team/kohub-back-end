package kr.kohub.type;

public enum PromotionOrderType {
  NO("id");

  private final String typeName;

  private PromotionOrderType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
