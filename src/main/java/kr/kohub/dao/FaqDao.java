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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.FaqDaoSql;
import kr.kohub.dto.Faq;
import kr.kohub.dto.param.FaqParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FaqDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Faq> rowMapper = BeanPropertyRowMapper.newInstance(Faq.class);
  private SimpleJdbcInsert insertAction;


  public FaqDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("faq")
        .usingGeneratedKeyColumns("id").usingColumns("title", "answer", "user_id");
  }

  public Faq selectById(int faqId) {
    Faq faq;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("faqId", faqId);

      faq = jdbc.queryForObject(FaqDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      faq = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      faq = null;
    }

    return faq;
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

  public int insert(FaqParam faqParam) {
    Map<String, Object> params = new HashMap<>();
    params.put("title", faqParam.getTitle());
    params.put("answer", faqParam.getAnswer());
    params.put("user_id", faqParam.getUserId());

    return insertAction.executeAndReturnKey(params).intValue();
  }

  public int update(int faqId, FaqParam faqParam, String modifyDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("faqId", faqId);
    params.put("title", faqParam.getTitle());
    params.put("answer", faqParam.getAnswer());
    params.put("modifyDate", modifyDate);

    return jdbc.update(FaqDaoSql.UPDATE, params);
  }

  public int deleteById(int faqId) {
    Map<String, Object> params = new HashMap<>();
    params.put("faqId", faqId);

    return jdbc.update(FaqDaoSql.DELETE_BY_ID, params);
  }
}
