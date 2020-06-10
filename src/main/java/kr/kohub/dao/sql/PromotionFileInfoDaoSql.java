package kr.kohub.dao.sql;

public class PromotionFileInfoDaoSql {
  public static final String SELECT_BY_ID =
      "SELECT promotion_file_info.id, file_name, save_file_name, content_type, promotion_file_info.create_date, promotion_file_info.modify_date, promotion_id, image_type.type FROM promotion_file_info LEFT JOIN image_type ON promotion_file_info.image_type_id = image_type.id WHERE promotion_file_info.id = :promotionFileInfoId";

  public static final String SELECT_BY_PROMOTION_ID =
      "SELECT promotion_file_info.id, file_name, save_file_name, content_type, promotion_file_info.create_date, promotion_file_info.modify_date, promotion_id, image_type.type FROM promotion_file_info LEFT JOIN image_type ON promotion_file_info.image_type_id = image_type.id WHERE promotion_id = :promotionId";

  public static final String DELETE_BY_ID =
      "DELETE FROM promotion_file_info WHERE id = :promotionFileInfoId";
}
