package kr.kohub.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.QnaCommentDaoSql;
import kr.kohub.dto.QnaComment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class QnaCommentDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<QnaComment> rowMapper =
      BeanPropertyRowMapper.newInstance(kr.kohub.dto.QnaComment.class);

  public QnaCommentDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public QnaComment selectedById(int qnaId) {
    QnaComment qnaCommnet;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("qnaId", qnaId);
      qnaCommnet = jdbc.queryForObject(QnaCommentDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (Exception e) {
      log.error(e.getMessage());
      qnaCommnet = null;
    }
    return qnaCommnet;
  }
}
