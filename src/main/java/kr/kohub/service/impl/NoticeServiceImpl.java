package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.NoticeBoardDao;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.param.NoticeBoardParam;
import kr.kohub.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {
  @Autowired
  private NoticeBoardDao noticeBoardDao;

  @Transactional(readOnly = true)
  @Override
  public List<NoticeBoard> getNotices(int start) {
    return noticeBoardDao.selectPaging(start);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalNoticeCount() {
    return noticeBoardDao.selectTotalNoticeCount();
  }

  @Transactional(readOnly = true)
  @Override
  public NoticeBoard getNotice(int noticeId) {
    return noticeBoardDao.selectById(noticeId);
  }

  @Transactional(readOnly = false)
  @Override
  public int addNotice(NoticeBoardParam noticeBoardParam) {
    return noticeBoardDao.insert(noticeBoardParam);
  }
}
