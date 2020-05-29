package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.PromotionOrderType;
import kr.kohub.type.PromotionStateType;

public interface PromotionService {
  public List<Promotion> getPromotions(int start, PromotionOrderType promotionOrderType,
      OrderOptionType orderOptionType);

  public List<Promotion> getPromotionsByState(int start, PromotionStateType promotionStateType,
      PromotionOrderType promotionOrderType, OrderOptionType orderOptionType);

  public int getTotalPromotionCount();

  public int getTotalPromotionCountByState(PromotionStateType promotionStateType);

  public int addPromotion(PromotionParam promotionParam, int userId);

  public int changeStateByPromotionId(int promotionId, PromotionStateType promotionStateType);
}
