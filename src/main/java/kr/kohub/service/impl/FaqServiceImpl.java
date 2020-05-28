package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.FaqDao;
import kr.kohub.dto.Faq;
import kr.kohub.service.FaqService;

@Service
public class FaqServiceImpl implements FaqService {
  @Autowired
  private FaqDao faqDao;

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

}
