package kr.kohub.type;

public enum UserRoleType {
  ADMIN("admin"), USER("user");

  private final String roleName;

  private UserRoleType(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return this.roleName;
  }
}
