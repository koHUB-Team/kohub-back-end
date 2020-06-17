package kr.kohub.dao.sql;

public class QnaDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT qa_board.id, title, category, content, user_id, qa_board.create_date, qa_board.modify_date, user.name AS user_name FROM qa_board LEFT JOIN user ON qa_board.user_id = user.id ORDER BY qa_board.id DESC limit :start, :limit";

  public static final String SELECT_TOTAL_QNA_COUNT = "SELECT count(*) FROM qa_board";

  public static final String SELECT_BY_TITLE =
      "SELECT qa_board.id, title, category, content, user_id, qa_board.create_date, qa_board.modify_date, user.name AS user_name FROM qa_board LEFT JOIN user ON qa_board.user_id = user.id WHERE title like :title";

  public static final String SELECT_BY_NAME =
      "SELECT qa_board.id, title, category, content, user_id, qa_board.create_date, qa_board.modify_date, user.name AS user_name FROM qa_board LEFT JOIN user ON qa_board.user_id = user.id WHERE user.name like :userName";

  public static final String SELECT_BY_ID =
      "SELECT qa_board.id, title, category, content, user_id, qa_board.create_date, qa_board.modify_date, user.name AS user_name FROM qa_board LEFT JOIN user ON qa_board.user_id = user.id WHERE qa_board.id = :qnaId";

  public static final String DELETE_BY_ID = "DELETE FROM qa_board WHERE qa_board.id = :qnaId";

  public static final String UPDATE =
      "UPDATE qa_board SET title=:title, content=:content, category=:category, modify_date=:modifyDate WHERE id=:qnaId";
}
