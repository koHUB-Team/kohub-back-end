package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.PromotionOrderType;

public interface PromotionService {
  public List<Promotion> getPromotions(int start, PromotionOrderType promotionOrderType,
      OrderOptionType orderOptionType);

  public int addPromotion(PromotionParam promotionParam, int userId);
}
