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
import kr.kohub.dao.sql.QnaDaoSql;
import kr.kohub.dto.Qna;
import kr.kohub.dto.param.QnaParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class QnaDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Qna> rowMapper = BeanPropertyRowMapper.newInstance(Qna.class);
  private SimpleJdbcInsert insertAction;

  public QnaDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("qa_board")
        .usingGeneratedKeyColumns("id").usingColumns("title", "content", "user_id", "category");
  }

  public List<Qna> selectPaging(int start) {
    List<Qna> qnas;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", QnaDaoSql.LIMIT);

      qnas = jdbc.query(QnaDaoSql.SELECT_PAGING, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      qnas = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      qnas = null;
    }
    return qnas;
  }

  public List<Qna> selectByTitle(String title) {
    List<Qna> qnas;

    try {
      Map<String, Object> params = new HashMap<>();

      String tmp = "%" + title + "%";
      params.put("title", tmp);

      qnas = jdbc.query(QnaDaoSql.SELECT_BY_TITLE, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      qnas = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      qnas = null;
    }
    return qnas;
  }

  public List<Qna> selectByName(String userName) {
    List<Qna> qnas;

    try {
      Map<String, Object> params = new HashMap<>();

      String tmp = "%" + userName + "%";
      params.put("userName", tmp);

      qnas = jdbc.query(QnaDaoSql.SELECT_BY_NAME, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      qnas = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      qnas = null;
    }

    return qnas;
  }

  public int selectTotalQnaCount() {
    int totalCount = 0;
    try {
      totalCount = jdbc.queryForObject(QnaDaoSql.SELECT_TOTAL_QNA_COUNT, Collections.emptyMap(),
          Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }
    return totalCount;
  }

  public Qna selectById(int qnaId) {
    Qna qna;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("qnaId", qnaId);
      qna = jdbc.queryForObject(QnaDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      qna = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      qna = null;
    }

    return qna;
  }

  public int insert(QnaParam qnaParam) {
    Map<String, Object> params = new HashMap<>();
    params.put("title", qnaParam.getTitle());
    params.put("content", qnaParam.getContent());
    params.put("category", qnaParam.getCategory());
    params.put("user_id", qnaParam.getUserId());

    return insertAction.execute(params);
  }

  public int delete(int qnaId) {
    Map<String, Object> params = new HashMap<>();
    params.put("qnaId", qnaId);

    return jdbc.update(QnaDaoSql.DELETE_BY_ID, params);
  }

  public int update(int qnaId, String title, String content, String category, String modifyDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("qnaId", qnaId);
    params.put("title", title);
    params.put("content", content);
    params.put("category", category);
    params.put("modifyDate", modifyDate);

    return jdbc.update(QnaDaoSql.UPDATE, params);

  }
}
