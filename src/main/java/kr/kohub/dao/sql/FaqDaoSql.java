package kr.kohub.dao.sql;

public class FaqDaoSql {
  public static final int LIMIT = 5;

  public static final String SELECT_PAGING =
      "SELECT id, title, answer, user_id, create_date, modify_date from faq limit :start, :limit";
  public static final String SELECT_TOTAL_FAQ_COUNT = "SELECT count(*) FROM faq";
}
