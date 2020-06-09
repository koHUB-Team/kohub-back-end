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
public class QnaComment {
  @NonNull
  private int id;

  @NonNull
  private int qaBoardId;

  @NonNull
  private int userId;

  @NonNull
  private String userName;

  @NonNull
  private String comment;

  @NonNull
  private String createDate;

  @NonNull
  private String modifyDate;
}
