package kr.kohub.dao.sql;

public class FaqDaoSql {
  public static final int LIMIT = 5;

  public static final String SELECT_PAGING =
      "SELECT id, title, answer, user_id, create_date, modify_date from faq limit :start, :limit";

  public static final String SELECT_TOTAL_FAQ_COUNT = "SELECT count(*) FROM faq";

  public static final String SELECT_BY_ID =
      "SELECT id, title, answer, user_id, create_date, modify_date from faq WHERE id = :faqId";

  public static final String UPDATE =
      "UPDATE faq SET title=:title, answer=:answer, modify_date=:modifyDate WHERE id=:faqId";

  public static final String DELETE_BY_ID = "DELETE FROM faq WHERE id = :faqId";
}
