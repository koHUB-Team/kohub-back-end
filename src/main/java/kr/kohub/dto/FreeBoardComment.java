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
public class FreeBoardComment {
  @NonNull
  private int id;

  @NonNull
  private int freeBoardId;

  @NonNull
  private String userName;

  @NonNull
  private String comment;

  @NonNull
  private String createDate;

  @NonNull
  private String modifyDate;
}
