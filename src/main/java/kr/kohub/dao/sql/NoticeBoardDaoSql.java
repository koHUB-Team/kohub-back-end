package kr.kohub.dao.sql;

public class NoticeBoardDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT notice_board.id, title, content, user_id, notice_board.create_date, notice_board.modify_date, user.name AS user_name FROM notice_board LEFT JOIN user ON notice_board.user_id = user.id limit :start, :limit";
}
