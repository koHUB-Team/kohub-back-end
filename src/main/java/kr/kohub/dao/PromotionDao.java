package kr.kohub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.PromotionDaoSql;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.PromotionOrderType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PromotionDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
  private SimpleJdbcInsert insertAction;

  public PromotionDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction =
        new SimpleJdbcInsert(dataSource).withTableName("promotion").usingGeneratedKeyColumns("id")
            .usingColumns("title", "email", "start_date", "end_date", "content", "user_id");
  }

  public List<Promotion> selectPaging(int start, PromotionOrderType promotionOrderType,
      OrderOptionType orderOptionType) {
    List<Promotion> promotions;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", PromotionDaoSql.LIMIT);

      promotions = jdbc.query(String.format(PromotionDaoSql.SELECT_PAGING,
          promotionOrderType.getTypeName(), orderOptionType.getTypeName()), params, rowMapper);

    } catch (EmptyResultDataAccessException e) {
      promotions = null;
    } catch (NullPointerException e) {
      promotions = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      promotions = null;
    }

    return promotions;
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
