package kr.kohub.dao.sql;

public class PromotionFileInfoDaoSql {
  public static final String SELECT_BY_PROMOTION_ID =
      "SELECT * FROM promotion_file_info WHERE promotion_id = :promotionId";

  public static final String DELETE_BY_ID =
      "DELETE FROM promotion_file_info WHERE id = :promotionFileInfoId";
}
