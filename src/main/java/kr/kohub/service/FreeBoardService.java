package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.FreeBoard;
import kr.kohub.dto.FreeBoardComment;
import kr.kohub.dto.param.FreeBoardCommentParam;
import kr.kohub.dto.param.FreeBoardParam;

public interface FreeBoardService {
  public List<FreeBoard> getFreeBoards(int start);

  public int getTotalFreeBoardCount();

  public List<FreeBoard> getFreeBoardsByTitle(String title);

  public List<FreeBoard> getFreeBoardsByName(String userName);

  public FreeBoard getFreeBoard(int freeId);

  public int addFreeBoard(FreeBoardParam freeBoardParam);

  public int removeFree(int freeId);

  public int changeFree(int freeId, String title, String content);

  public List<FreeBoardComment> getComments(int freeId, int start);

  public int getTotalCommentCount(int freeId);

  public int addComment(FreeBoardCommentParam freeBoardCommentParam);

  public int removeComment(int commentId);

  public int changeComment(int commentId, String comment);
}
