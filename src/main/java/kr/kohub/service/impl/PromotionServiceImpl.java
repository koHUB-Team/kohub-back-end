package kr.kohub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import kr.kohub.dao.PromotionDao;
import kr.kohub.dao.PromotionFileInfoDao;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.service.PromotionService;
import kr.kohub.type.FilePathType;
import kr.kohub.util.FileUtil;

@Service
public class PromotionServiceImpl implements PromotionService {
  @Autowired
  PromotionDao promotionDao;

  @Autowired
  PromotionFileInfoDao promotionFileInfoDao;

  @Autowired
  FileUtil fileUtil;

  @Transactional(readOnly = false)
  @Override
  public int addPromotion(PromotionParam promotionParam, int userId) {
    int promotionId = promotionDao.insert(promotionParam, userId);

    MultipartFile promotinImageFile = promotionParam.getPromotionImage();

    String saveFileName = fileUtil.upload(promotinImageFile, FilePathType.PROMOTION);
    String fileName = promotinImageFile.getOriginalFilename();
    String contentType = promotinImageFile.getContentType();

    PromotionFileInfo promotionFileInfo = PromotionFileInfo.builder().fileName(fileName)
        .saveFileName(saveFileName).contentType(contentType).promotionId(promotionId).build();

    promotionFileInfoDao.insert(promotionFileInfo);

    return 0;
  }

}
