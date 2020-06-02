package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.PromotionFileInfo;
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

  @NonNull
  private List<PromotionFileInfo> promotionImages;

  private int totalCount;


  private int totalPromotionCount;


  private int totalPromotingCount;


  private int totalWaitingCount;
}
