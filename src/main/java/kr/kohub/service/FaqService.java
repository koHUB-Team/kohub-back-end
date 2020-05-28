package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.Faq;

public interface FaqService {
  public List<Faq> getFaqs(int start);

  public int getTotalFaqCount();
}
