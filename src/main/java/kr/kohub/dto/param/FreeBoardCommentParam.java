package kr.kohub.dto.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeBoardCommentParam {
  @NonNull
  private int freeBoardId;

  @NonNull
  private int userId;

  @NonNull
  private String comment;
}
