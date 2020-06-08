package kr.kohub.dto.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaParam {
  @NonNull
  @NotEmpty(message = "제목은 반드시 입력해야합니다.")
  private String title;

  @NonNull
  @NotEmpty(message = "공지사항 내용은 반드시 입력해야합니다.")
  private String content;

  @NonNull
  @NotEmpty(message = "카테고리를 반드시 입력해야 합니다.")
  private String category;

  @Positive
  private int userId;

}
