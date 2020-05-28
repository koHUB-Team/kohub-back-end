package kr.kohub.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.kohub.dto.PromotionFileInfo;

@Repository
public class PromotionFileInfoDao {
  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;

  public PromotionFileInfoDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("promotion_file_info")
        .usingGeneratedKeyColumns("id").usingColumns("file_name", "save_file_name", "content_type",
            "promotion_id", "image_type_id");
  }

  public int insert(PromotionFileInfo promotionFileInfo) {
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(promotionFileInfo);
    return insertAction.execute(params);
  }
}
