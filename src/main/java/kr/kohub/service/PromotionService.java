package kr.kohub.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.PromotionOrderType;
import kr.kohub.type.PromotionStateType;

public interface PromotionService {
  public Promotion getPromotionById(int promotionId);

  public List<Promotion> getPromotions(int start, PromotionOrderType promotionOrderType,
      OrderOptionType orderOptionType);

  public List<Promotion> getPromotionsByState(int start, PromotionStateType promotionStateType,
      PromotionOrderType promotionOrderType, OrderOptionType orderOptionType);

  public int getTotalPromotionCount();

  public int getTotalPromotionCountByState(PromotionStateType promotionStateType);

  public void addPromotion(PromotionParam promotionParam, int userId);

  public int addPromotionImage(MultipartFile promotionImage, int promotionId);

  public void changePromotionByPromotionId(int promotionId, PromotionParam promotionParam);

  public int changeStateByPromotionId(int promotionId, PromotionStateType promotionStateType);

  public int removePromotionById(int promotionId);

  public PromotionFileInfo getPromotionImageById(int promotionFileInfoId);

  public List<PromotionFileInfo> getPromotionImages(int promotionId);
}