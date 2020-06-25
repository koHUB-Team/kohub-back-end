package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.Faq;
import kr.kohub.dto.param.FaqParam;

public interface FaqService {
  public Faq getFaqById(int faqId);

  public List<Faq> getFaqs(int start);

  public int getTotalFaqCount();

  public void addFaq(FaqParam faqParam);

  public int changeFaqById(int faqId, FaqParam faqParam);

  public int removeByFaqId(int faqId);
}
