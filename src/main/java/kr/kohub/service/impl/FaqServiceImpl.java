package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.FaqDao;
import kr.kohub.dto.Faq;
import kr.kohub.dto.param.FaqParam;
import kr.kohub.service.FaqService;
import kr.kohub.util.DateUtil;

@Service
public class FaqServiceImpl implements FaqService {
  @Autowired
  private FaqDao faqDao;

  @Transactional(readOnly = true)
  @Override
  public Faq getFaqById(int faqId) {
    return faqDao.selectById(faqId);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Faq> getFaqs(int start) {
    return faqDao.selectPaging(start);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalFaqCount() {
    return faqDao.seletTotalFaqCount();
  }

  @Transactional(readOnly = false)
  @Override
  public void addFaq(FaqParam faqParam) {
    int insertCount = faqDao.insert(faqParam);

    if (insertCount == 0) {
      throw new RuntimeException("FAQ did not insert Exception");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public int changeFaqById(int faqId, FaqParam faqParam) {
    String modifyDate = DateUtil.getNowDate();
    return faqDao.update(faqId, faqParam, modifyDate);
  }

  @Transactional(readOnly = false)
  @Override
  public int removeByFaqId(int faqId) {
    int deleteCount = 0;
    deleteCount = faqDao.deleteById(faqId);

    return deleteCount;
  }
}
