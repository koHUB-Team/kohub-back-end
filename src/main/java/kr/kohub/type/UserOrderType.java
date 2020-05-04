package kr.kohub.type;

public enum UserOrderType {
  NO("id"), CREATE_DATE("create_date"), MODIFY_DATE("modify_date");

  private final String typeName;

  private UserOrderType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
