package kr.kohub.dao.sql;

public class PromotionDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_BY_ID =
      "SELECT promotion.id, title, promotion.email, user.name AS user_name, start_date, end_date, content, promotion.create_date, promotion.modify_date, promotion.state FROM promotion LEFT JOIN user ON promotion.user_id = user.id WHERE promotion.id = :promotionId";

  public static final String SELECT_PAGING =
      "SELECT promotion.id, title, promotion.email, user.name AS user_name, start_date, end_date, content, promotion.create_date, promotion.modify_date, promotion.state FROM promotion LEFT JOIN user ON promotion.user_id = user.id ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_PAGING_BY_STATE =
      "SELECT promotion.id, title, promotion.email, user.name AS user_name, start_date, end_date, content, promotion.create_date, promotion.modify_date, promotion.state FROM promotion LEFT JOIN user ON promotion.user_id = user.id WHERE promotion.state = :promotionStateType ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_TOTAL_PROMOTION_COUNT = "SELECT count(*) FROM promotion";

  public static final String SELECT_TOTAL_PROMOTION_COUNT_BY_STATE =
      "SELECT count(*) FROM promotion WHERE state = :promotionStateType";

  public static final String UPDATE_STATE =
      "UPDATE promotion SET state=:state, modify_date=:modifyDate WHERE id=:promotionId";

  public static final String DELETE_BY_ID = "DELETE FROM promotion WHERE id = :promotionId";
}
