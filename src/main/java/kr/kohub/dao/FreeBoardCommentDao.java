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
import kr.kohub.dao.sql.FreeBoardCommentDaoSql;
import kr.kohub.dto.FreeBoardComment;
import kr.kohub.dto.param.FreeBoardCommentParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FreeBoardCommentDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<FreeBoardComment> rowMapper =
      BeanPropertyRowMapper.newInstance(FreeBoardComment.class);
  private SimpleJdbcInsert insertAction;

  public FreeBoardCommentDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("free_board_comment")
        .usingGeneratedKeyColumns("id").usingColumns("free_board_id", "user_id", "comment");
  }

  public List<FreeBoardComment> selectByFreeId(int freeId, int start) {
    List<FreeBoardComment> comments;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("freeId", freeId);
      params.put("start", start);
      params.put("limit", FreeBoardCommentDaoSql.LIMIT);

      comments = jdbc.query(FreeBoardCommentDaoSql.SELECT_BY_FREE_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      comments = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      comments = null;
    }

    return comments;
  }

  public int selectTotalCommentCountById(int freeId) {
    int totalCommentCount = 0;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("freeId", freeId);

      totalCommentCount = jdbc.queryForObject(FreeBoardCommentDaoSql.SELECT_COMMENT_COUNT_BY_ID,
          params, Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCommentCount = 0;
    }
    return totalCommentCount;
  }

  public int insert(FreeBoardCommentParam freeBoardCommentParam) {
    Map<String, Object> params = new HashMap<>();
    params.put("free_board_id", freeBoardCommentParam.getFreeBoardId());
    params.put("user_id", freeBoardCommentParam.getUserId());
    params.put("comment", freeBoardCommentParam.getComment());

    return insertAction.execute(params);
  }

  public int delete(int commentId) {
    Map<String, Object> params = new HashMap<>();
    params.put("commentId", commentId);

    return jdbc.update(FreeBoardCommentDaoSql.DELETE_BY_ID, params);
  }

  public int update(int commentId, String comment, String modifyDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("commentId", commentId);
    params.put("comment", comment);
    params.put("modifyDate", modifyDate);

    return jdbc.update(FreeBoardCommentDaoSql.UPDATE, params);
  }
}
