package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.FreeBoardCommentDao;
import kr.kohub.dao.FreeBoardDao;
import kr.kohub.dto.FreeBoard;
import kr.kohub.dto.FreeBoardComment;
import kr.kohub.dto.param.FreeBoardCommentParam;
import kr.kohub.dto.param.FreeBoardParam;
import kr.kohub.service.FreeBoardService;
import kr.kohub.util.DateUtil;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {
  @Autowired
  private FreeBoardDao freeBoardDao;

  @Autowired
  private FreeBoardCommentDao freeBoardCommentDao;

  @Transactional(readOnly = true)
  @Override
  public List<FreeBoard> getFreeBoards(int start) {
    return freeBoardDao.selectPaging(start);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalFreeBoardCount() {
    return freeBoardDao.selectTotalFreeBoardCount();
  }

  @Transactional(readOnly = true)
  @Override
  public List<FreeBoard> getFreeBoardsByTitle(String title) {
    return freeBoardDao.selectByTitle(title);
  }

  @Transactional(readOnly = true)
  @Override
  public List<FreeBoard> getFreeBoardsByName(String userName) {
    return freeBoardDao.selectByName(userName);
  }

  @Transactional(readOnly = true)
  @Override
  public FreeBoard getFreeBoard(int freeId) {
    return freeBoardDao.selectById(freeId);
  }

  @Transactional(readOnly = false)
  @Override
  public int addFreeBoard(FreeBoardParam freeBoardParam) {
    return freeBoardDao.insert(freeBoardParam);
  }

  @Transactional(readOnly = false)
  @Override
  public int removeFree(int freeId) {
    return freeBoardDao.delete(freeId);
  }

  @Transactional(readOnly = false)
  @Override
  public int changeFree(int freeId, String title, String content) {
    String modifyDate = DateUtil.getNowDate();
    return freeBoardDao.update(freeId, title, content, modifyDate);
  }

  @Transactional(readOnly = true)
  @Override
  public List<FreeBoardComment> getComments(int freeId) {
    return freeBoardCommentDao.selectByFreeId(freeId);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalCommentCount(int freeId) {
    return freeBoardCommentDao.selectTotalCommentCountById(freeId);
  }

  @Transactional(readOnly = false)
  @Override
  public int addComment(FreeBoardCommentParam freeBoardCommentParam) {
    return freeBoardCommentDao.insert(freeBoardCommentParam);
  }

  @Transactional(readOnly = false)
  @Override
  public int removeComment(int commentId) {
    return freeBoardCommentDao.delete(commentId);
  }

  @Transactional(readOnly = false)
  @Override
  public int changeComment(int commentId, String comment) {
    String modifyDate = DateUtil.getNowDate();
    return freeBoardCommentDao.update(commentId, comment, modifyDate);
  }
}
