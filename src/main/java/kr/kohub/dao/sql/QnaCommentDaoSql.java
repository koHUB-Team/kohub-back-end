package kr.kohub.dao.sql;

public class QnaCommentDaoSql {
  public static final String SELECT_BY_ID =
      "SELECT qa_board_comment.id, qa_board_id, user_id, comment, qa_board_comment.create_date, qa_board_comment.modify_date, user.name AS user_name from qa_board_comment LEFT JOIN user ON qa_board_comment.user_id = user.id WHERE qa_board_id = :qnaId";
}
