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
public class UserParam {
  @NotBlank(message = "계정 상태정보가 필요합니다.")
  private String state;
}
