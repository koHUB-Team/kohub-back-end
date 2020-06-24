package kr.kohub.dao.sql;

public class FreeBoardCommentDaoSql {
  public static final int LIMIT = 5;

  public static final String SELECT_BY_FREE_ID =
      "SELECT free_board_comment.id, free_board_id, user_id, comment, free_board_comment.create_date, free_board_comment.modify_date, user.name AS user_name from free_board_comment LEFT JOIN user ON free_board_comment.user_id = user.id WHERE free_board_id = :freeId ORDER BY free_board_comment.id DESC limit :start, :limit";

  public static final String SELECT_COMMENT_COUNT_BY_ID =
      "SELECT count(*) FROM free_board_comment WHERE free_board_id = :freeId";

  public static final String DELETE_BY_ID = "DELETE FROM free_board_comment where id = :commentId";

  public static final String UPDATE =
      "UPDATE free_board_comment SET comment=:comment, modify_date=:modifyDate WHERE id=:commentId";
}
