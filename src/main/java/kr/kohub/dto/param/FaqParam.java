package kr.kohub.dto.param;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqParam {
  @NotBlank(message = "질문내용은 반드시 입력해야합니다.")
  private String title;

  @NotBlank(message = "답변내용은 반드시 입력해야합니다.")
  private String answer;

  private int userId = 1; // 이 부분은 나중에 로그인 처리시 구현할 것
}
