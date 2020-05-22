package kr.kohub.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.kohub.dto.param.PromotionParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PromotionDao {
  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;

  public PromotionDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction =
        new SimpleJdbcInsert(dataSource).withTableName("promotion").usingGeneratedKeyColumns("id")
            .usingColumns("title", "email", "start_date", "end_date", "content", "user_id");
  }

  public int insert(PromotionParam promotionParam, int userId) {
    Map<String, Object> params = new HashMap<>();
    params.put("title", promotionParam.getTitle());
    params.put("email", promotionParam.getEmail());
    params.put("start_date", promotionParam.getStartDate());
    params.put("end_date", promotionParam.getEndDate());
    params.put("content", promotionParam.getContent());
    params.put("user_id", userId);

    return insertAction.executeAndReturnKey(params).intValue();
  }
}
