package kr.kohub.service;

import kr.kohub.dto.param.PromotionParam;

public interface PromotionService {
  public int addPromotion(PromotionParam promotionParam, int userId);
}
