package kr.kohub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionFileInfo {
  private int id;

  @NonNull
  private String fileName;

  @NonNull
  private String saveFileName;

  @NonNull
  private String contentType;

  private String createDate;
  private String modifyDate;

  @NonNull
  private int promotionId;
}
