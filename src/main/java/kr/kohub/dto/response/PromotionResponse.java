package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Promotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionResponse {
  @NonNull
  private List<Promotion> promotions;
}
