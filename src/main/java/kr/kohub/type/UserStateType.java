package kr.kohub.type;

public enum UserStateType {
  NORMAL("정상"), WARRNING("경고"), VAN("정지");

  private final String stateName;

  private UserStateType(String stateName) {
    this.stateName = stateName;
  }

  public String getStateName() {
    return this.stateName;
  }
}
