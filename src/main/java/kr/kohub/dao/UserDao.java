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
import kr.kohub.dao.sql.UserDaoSql;
import kr.kohub.dto.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

  public UserDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<User> selectPaging(int start) {
    List<User> users;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", UserDaoSql.LIMIT);

      users = jdbc.query(UserDaoSql.SELECT_PAGING, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      users = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      users = null;
    }

    return users;
  }

  public int selectTotalCount() {
    int totalCount;
    try {
      totalCount =
          jdbc.queryForObject(UserDaoSql.SELECT_TOTAL_COUNT, Collections.emptyMap(), Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }

    return totalCount;
  }
}
