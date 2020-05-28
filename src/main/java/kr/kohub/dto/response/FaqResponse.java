package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqResponse {

  @NonNull
  private List<Faq> faqs;

  @NonNull
  private int totalFaqCount;
}
