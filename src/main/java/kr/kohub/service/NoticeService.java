package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.param.NoticeBoardParam;

public interface NoticeService {
  public List<NoticeBoard> getNotices(int start);

  public int getTotalNoticeCount();

  public NoticeBoard getNotice(int noticeId);

  public int addNotice(NoticeBoardParam noticeBoardParam);

  public int deleteNotice(int noticeId);

  public int updateNotice(int noticeId, String title, String content);
}
