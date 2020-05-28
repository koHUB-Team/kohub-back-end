package kr.kohub.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.FaqDaoSql;
import kr.kohub.dto.Faq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FaqDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Faq> rowMapper = BeanPropertyRowMapper.newInstance(Faq.class);

  public FaqDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Faq> selectPaging(int start) {
    List<Faq> faqs;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", FaqDaoSql.LIMIT);

      faqs = jdbc.query(FaqDaoSql.SELECT_PAGING, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      faqs = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      faqs = null;
    }

    return faqs;
  }

  public int seletTotalFaqCount() {
    int totalCount = 0;

    try {
      totalCount = jdbc.queryForObject(FaqDaoSql.SELECT_TOTAL_FAQ_COUNT, Collections.emptyMap(),
          Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }

    return totalCount;
  }
}
