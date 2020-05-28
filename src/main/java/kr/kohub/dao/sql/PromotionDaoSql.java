package kr.kohub.dao.sql;

public class PromotionDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT promotion.id, title, promotion.email, user.name AS user_name, start_date, end_date, content, promotion.create_date, promotion.modify_date, promotion.state FROM promotion LEFT JOIN user ON promotion.user_id = user.id ORDER BY %s %s LIMIT :start, :limit";
}
