package kr.kohub.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.exception.FileInfoNotFoundException;
import kr.kohub.service.PromotionService;
import kr.kohub.type.FilePathType;
import kr.kohub.util.FileUtil;

@Controller
@RequestMapping(path = "/download")
public class KohubFileController {
  @Autowired
  PromotionService promotionService;

  @Autowired
  FileUtil fileUtil;

  @CrossOrigin
  @GetMapping(path = "/promotions/{promotionImageId}")
  public void getPromotionImage(@PathVariable(name = "promotionImageId") int promotionImageId,
      HttpServletResponse response) {

    PromotionFileInfo promotionFileInfo = promotionService.getPromotionImageById(promotionImageId);
    if (promotionFileInfo == null) {
      throw new FileInfoNotFoundException();
    }

    String fileName = promotionFileInfo.getFileName();
    String saveFileName = promotionFileInfo.getSaveFileName();
    String contentType = promotionFileInfo.getContentType();
    fileUtil.download(fileName, saveFileName, contentType, FilePathType.PROMOTION, response);
  }
}
