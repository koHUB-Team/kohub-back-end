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
import kr.kohub.dao.sql.NoticeBoardDaoSql;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.param.NoticeBoardParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class NoticeBoardDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<NoticeBoard> rowMapper = BeanPropertyRowMapper.newInstance(NoticeBoard.class);
  private SimpleJdbcInsert insertAction;

  public NoticeBoardDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("notice_board")
        .usingGeneratedKeyColumns("id").usingColumns("title", "content", "user_id");
  }

  public List<NoticeBoard> selectPaging(int start) {
    List<NoticeBoard> noticeBoards;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", NoticeBoardDaoSql.LIMIT);

      noticeBoards = jdbc.query(NoticeBoardDaoSql.SELECT_PAGING, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      noticeBoards = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      noticeBoards = null;
    }

    return noticeBoards;
  }

  public int selectTotalNoticeCount() {
    int totalCount = 0;
    try {
      totalCount = jdbc.queryForObject(NoticeBoardDaoSql.SELECT_TOTAL_NOTICE_COUNT,
          Collections.emptyMap(), Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }
    return totalCount;
  }

  public NoticeBoard selectById(int noticeId) {
    NoticeBoard noticeBoard;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("noticeId", noticeId);
      noticeBoard = jdbc.queryForObject(NoticeBoardDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      noticeBoard = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      noticeBoard = null;
    }

    return noticeBoard;
  }

  public int insert(NoticeBoardParam noticeBoardParam) {
    Map<String, Object> params = new HashMap<>();
    params.put("title", noticeBoardParam.getTitle());
    params.put("content", noticeBoardParam.getContent());
    params.put("user_id", noticeBoardParam.getUserId());

    int insertCount = this.insertAction.execute(params);
    return insertCount;
  }
}
