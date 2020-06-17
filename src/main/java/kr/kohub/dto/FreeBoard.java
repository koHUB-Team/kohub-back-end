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
public class FreeBoard {
  @NonNull
  private int id;
  @NonNull
  private String title;
  @NonNull
  private String content;
  @NonNull
  private String createDate;
  @NonNull
  private String modifyDate;
  @NonNull
  private int userId;
  @NonNull
  private String userName;
}
