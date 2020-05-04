package kr.kohub.type;

public enum UserAuthType {
  CERTIFIED(true), UNCERTIFIED(false);

  private final boolean authFlag;

  private UserAuthType(boolean authFlag) {
    this.authFlag = authFlag;
  }

  public boolean getAuthFlag() {
    return this.authFlag;
  }
}
