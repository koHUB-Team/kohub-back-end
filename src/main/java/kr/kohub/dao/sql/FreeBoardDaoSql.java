package kr.kohub.dao.sql;

public class FreeBoardDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT free_board.id, title, content, user_id, free_board.create_date, free_board.modify_date, user.name AS user_name FROM free_board LEFT JOIN user ON free_board.user_id = user.id ORDER BY free_board.id DESC limit :start, :limit";

  public static final String SELECT_TOTAL_FREEBOARD_COUNT = "SELECT count(*) FROM free_board";

  public static final String SELECT_BY_TITLE =
      "SELECT free_board.id, title, content, user_id, free_board.create_date, free_board.modify_date, user.name AS user_name FROM free_board LEFT JOIN user ON free_board.user_id = user.id WHERE title like :title";

  public static final String SELECT_BY_NAME =
      "SELECT free_board.id, title, content, user_id, free_board.create_date, free_board.modify_date, user.name AS user_name FROM free_board LEFT JOIN user ON free_board.user_id = user.id WHERE user.name like :userName";

  public static final String SELECT_BY_ID =
      "SELECT free_board.id, title, content, user_id, free_board.create_date, free_board.modify_date, user.name AS user_name FROM free_board LEFT JOIN user ON free_board.user_id = user.id WHERE free_board.id = :freeId";

  public static final String DELETE_BY_ID = "DELETE FROM free_board WHERE free_board.id = :freeId";

  public static final String UPDATE =
      "UPDATE free_board SET title=:title, content=:content, modify_date=:modifyDate WHERE id=:freeId";
}
