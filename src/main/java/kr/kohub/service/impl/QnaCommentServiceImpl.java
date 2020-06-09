package kr.kohub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.QnaCommentDao;
import kr.kohub.dto.QnaComment;
import kr.kohub.service.QnaCommentService;

@Service
public class QnaCommentServiceImpl implements QnaCommentService {
  @Autowired
  private QnaCommentDao qnaCommentDao;

  @Transactional(readOnly = true)
  @Override
  public QnaComment geteQnaComment(int qnaId) {
    return qnaCommentDao.selectedById(qnaId);
  }
}
