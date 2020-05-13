package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.NoticeBoardDao;
import kr.kohub.dto.NoticeBoard;
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

}
