package kr.kohub.dto.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionParam {
  @JsonIgnore
  private MultipartFile promotionImage;

  @NotBlank(message = "제목은 반드시 입력해야합니다.")
  private String title;

  @NotBlank
  @Email(message = "이메일 형식을 지켜야합니다.")
  private String email;

  @NotBlank
  private String startDate;

  @NotBlank
  private String endDate;

  @NotBlank
  private String content;
}
