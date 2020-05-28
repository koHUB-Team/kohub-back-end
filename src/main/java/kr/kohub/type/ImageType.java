package kr.kohub.type;

public enum ImageType {
  MA(1), TH(2);

  private final int imageTypeId;

  private ImageType(int imageTypeId) {
    this.imageTypeId = imageTypeId;
  }

  public int getImageTypeId() {
    return this.imageTypeId;
  }
}
