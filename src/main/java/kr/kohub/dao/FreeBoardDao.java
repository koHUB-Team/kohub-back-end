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
import kr.kohub.dao.sql.FreeBoardDaoSql;
import kr.kohub.dto.FreeBoard;
import kr.kohub.dto.param.FreeBoardParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FreeBoardDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<FreeBoard> rowMapper = BeanPropertyRowMapper.newInstance(FreeBoard.class);
  private SimpleJdbcInsert insertAction;

  public FreeBoardDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("free_board")
        .usingGeneratedKeyColumns("id").usingColumns("title", "content", "user_id");
  }

  public List<FreeBoard> selectPaging(int start) {
    List<FreeBoard> freeBoards;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", FreeBoardDaoSql.LIMIT);

      freeBoards = jdbc.query(FreeBoardDaoSql.SELECT_PAGING, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      freeBoards = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      freeBoards = null;
    }
    return freeBoards;
  }

  public int selectTotalFreeBoardCount() {
    int totalCount = 0;
    try {
      totalCount = jdbc.queryForObject(FreeBoardDaoSql.SELECT_TOTAL_FREEBOARD_COUNT,
          Collections.emptyMap(), Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }
    return totalCount;
  }

  public List<FreeBoard> selectByTitle(String title) {
    List<FreeBoard> freeBoards;

    try {
      Map<String, Object> params = new HashMap<>();

      String tmp = "%" + title + "%";
      params.put("title", tmp);

      freeBoards = jdbc.query(FreeBoardDaoSql.SELECT_BY_TITLE, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      freeBoards = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      freeBoards = null;
    }
    return freeBoards;
  }

  public List<FreeBoard> selectByName(String userName) {
    List<FreeBoard> freeBoards;

    try {
      Map<String, Object> params = new HashMap<>();

      String tmp = "%" + userName + "%";
      params.put("userName", tmp);

      freeBoards = jdbc.query(FreeBoardDaoSql.SELECT_BY_NAME, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      freeBoards = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      freeBoards = null;
    }
    return freeBoards;
  }

  public FreeBoard selectById(int freeId) {
    FreeBoard freeBoard;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("freeId", freeId);
      freeBoard = jdbc.queryForObject(FreeBoardDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      freeBoard = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      freeBoard = null;
    }

    return freeBoard;
  }

  public int insert(FreeBoardParam freeBoardParam) {
    Map<String, Object> params = new HashMap<>();
    params.put("title", freeBoardParam.getTitle());
    params.put("content", freeBoardParam.getContent());
    params.put("user_id", freeBoardParam.getUserId());

    return insertAction.execute(params);
  }

  public int delete(int freeId) {
    Map<String, Object> params = new HashMap<>();
    params.put("freeId", freeId);

    return jdbc.update(FreeBoardDaoSql.DELETE_BY_ID, params);
  }

  public int update(int freeId, String title, String content, String modifyDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("freeId", freeId);
    params.put("title", title);
    params.put("content", content);
    params.put("modifyDate", modifyDate);

    return jdbc.update(FreeBoardDaoSql.UPDATE, params);
  }

}
