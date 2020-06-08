package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.Qna;
import kr.kohub.dto.param.QnaParam;

public interface QnaService {
  public List<Qna> getQnas(int start);

  public int getTotalQnaCount();

  public List<Qna> getQnasByTitle(String title);

  public List<Qna> getQnasByName(String userName);

  public Qna getQna(int qnaId);

  public int addQna(QnaParam qnaParam);

  public int removeQna(int qnaId);

  public int changeQna(int qnaId, String title, String content, String category);
}
