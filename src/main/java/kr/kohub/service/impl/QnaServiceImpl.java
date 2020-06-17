package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.QnaCommentDao;
import kr.kohub.dao.QnaDao;
import kr.kohub.dto.Qna;
import kr.kohub.dto.QnaComment;
import kr.kohub.dto.param.QnaParam;
import kr.kohub.service.QnaService;
import kr.kohub.util.DateUtil;

@Service
public class QnaServiceImpl implements QnaService {
  @Autowired
  private QnaDao qnaDao;
  @Autowired
  private QnaCommentDao qnaCommentDao;

  @Transactional(readOnly = true)
  @Override
  public List<Qna> getQnas(int start) {
    return qnaDao.selectPaging(start);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalQnaCount() {
    return qnaDao.selectTotalQnaCount();
  }

  @Transactional(readOnly = true)
  @Override
  public List<Qna> getQnasByTitle(String title) {
    return qnaDao.selectByTitle(title);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Qna> getQnasByName(String userName) {
    return qnaDao.selectByName(userName);
  }

  @Transactional(readOnly = true)
  @Override
  public Qna getQna(int qnaId) {
    return qnaDao.selectById(qnaId);
  }

  @Transactional(readOnly = false)
  @Override
  public int addQna(QnaParam qnaParam) {
    return qnaDao.insert(qnaParam);
  }

  @Transactional(readOnly = false)
  @Override
  public int removeQna(int qnaId) {
    return qnaDao.delete(qnaId);
  }

  @Transactional(readOnly = false)
  @Override
  public int changeQna(int qnaId, String title, String content, String category) {
    String modifyDate = DateUtil.getNowDate();
    return qnaDao.update(qnaId, title, content, category, modifyDate);
  }

  @Transactional(readOnly = true)
  @Override
  public QnaComment getComment(int qnaId) {
    return qnaCommentDao.selectedById(qnaId);
  }
}
