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
public class Promotion {
  @NonNull
  private String id;

  @NonNull
  private String title;

  @NonNull
  private String email;

  @NonNull
  private String startDate;

  @NonNull
  private String endDate;

  @NonNull
  private String content;

  @NonNull
  private String userName;

  @NonNull
  private String createDate;

  @NonNull
  private String modifyDate;

  @NonNull
  private String state;
}
