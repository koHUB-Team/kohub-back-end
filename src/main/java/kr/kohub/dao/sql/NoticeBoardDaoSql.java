package kr.kohub.dao.sql;

public class NoticeBoardDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT notice_board.id, title, content, user_id, notice_board.create_date, notice_board.modify_date, user.name AS user_name FROM notice_board LEFT JOIN user ON notice_board.user_id = user.id ORDER BY notice_board.id DESC limit :start, :limit";

  public static final String SELECT_TOTAL_NOTICE_COUNT = "SELECT count(*) FROM notice_board";

  public static final String SELECT_BY_ID =
      "SELECT notice_board.id, title, content, user_id, notice_board.create_date, notice_board.modify_date, user.name AS user_name FROM notice_board LEFT JOIN user ON notice_board.user_id = user.id WHERE notice_board.id = :noticeId";
}
