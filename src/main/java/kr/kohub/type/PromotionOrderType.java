package kr.kohub.type;

public enum PromotionOrderType {
  NO("id"), START_DATE("start_date"), CREATE_DATE("create_date"), MODIFY_DATE("modify_date");

  private final String typeName;

  private PromotionOrderType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
