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
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.UserAuthType;
import kr.kohub.type.UserOrderType;
import kr.kohub.type.UserRoleType;
import kr.kohub.type.UserStateType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

  public UserDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<User> selectPaging(int start, UserOrderType userOrderType,
      OrderOptionType orderOptionType) {
    List<User> users = null;;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", UserDaoSql.LIMIT);

      users = jdbc.query(String.format(UserDaoSql.SELECT_PAGING, userOrderType.getTypeName(),
          orderOptionType.getTypeName()), params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      users = null;
    } catch (NullPointerException e) {
      users = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      users = null;
    }

    return users;
  }

  public List<User> selectPagingByRole(int start, UserRoleType userRoleType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    List<User> users = null;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", UserDaoSql.LIMIT);
      params.put("userRoleType", userRoleType.getRoleName());

      System.out.println(params.toString());

      users = jdbc.query(String.format(UserDaoSql.SELECT_PAGING_BY_ROLE,
          userOrderType.getTypeName(), orderOptionType.getTypeName()), params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      users = null;
    } catch (NullPointerException e) {
      users = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      users = null;
    }

    return users;
  }

  public List<User> selectPagingByAuth(int start, UserAuthType userAuthType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    List<User> users = null;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", UserDaoSql.LIMIT);
      params.put("userAuthType", userAuthType.getAuthFlag());

      users = jdbc.query(String.format(UserDaoSql.SELECT_PAGING_BY_AUTH,
          userOrderType.getTypeName(), orderOptionType.getTypeName()), params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      users = null;
    } catch (NullPointerException e) {
      users = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      users = null;
    }

    return users;
  }

  public List<User> selectPagingByState(int start, UserStateType userStateType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    List<User> users = null;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("start", start);
      params.put("limit", UserDaoSql.LIMIT);
      params.put("userStateType", userStateType.getStateName());

      users = jdbc.query(String.format(UserDaoSql.SELECT_PAGING_BY_STATE,
          userOrderType.getTypeName(), orderOptionType.getTypeName()), params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      users = null;
    } catch (NullPointerException e) {
      users = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      users = null;
    }

    return users;
  }

  public int selectTotalUserCount() {
    int totalCount;
    try {
      totalCount = jdbc.queryForObject(UserDaoSql.SELECT_TOTAL_USER_COUNT, Collections.emptyMap(),
          Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }

    return totalCount;
  }

  public int selectTotalUserCountByRole(UserRoleType userRoleType) {
    int totalCount;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("userRoleType", userRoleType.getRoleName());

      totalCount =
          jdbc.queryForObject(UserDaoSql.SELECT_TOTAL_USER_COUNT_BY_ROLE, params, Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }

    return totalCount;
  }

  public int selectTotalUserCountByAuth(UserAuthType userAuthType) {
    int totalCount;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("userAuthType", userAuthType.getAuthFlag());

      totalCount =
          jdbc.queryForObject(UserDaoSql.SELECT_TOTAL_USER_COUNT_BY_AUTH, params, Integer.class);
    } catch (Exception e) {
      log.error(e.getMessage());
      totalCount = 0;
    }

    return totalCount;
  }

  public int selectTotalUserCountByState(UserStateType userStateType) {
    int stateCount;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("userStateType", userStateType.getStateName());

      stateCount =
          jdbc.queryForObject(UserDaoSql.SELECT_TOTAL_USER_COUNT_BY_STATE, params, Integer.class);
    } catch (EmptyResultDataAccessException e) {
      stateCount = 0;
    } catch (NullPointerException e) {
      stateCount = 0;
    } catch (Exception e) {
      log.error(e.getMessage());
      stateCount = 0;
    }

    return stateCount;
  }

  public User selectByEmail(String email) {
    User user;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("email", email);

      user = jdbc.queryForObject(UserDaoSql.SELECT_BY_EMAIL, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      user = null;
    } catch (NullPointerException e) {
      user = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      user = null;
    }

    return user;
  }

  public User selectByName(String name) {
    User user;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("name", name);

      user = jdbc.queryForObject(UserDaoSql.SELECT_BY_NAME, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      user = null;
    } catch (NullPointerException e) {
      user = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      user = null;
    }

    return user;
  }

  public int updateState(int userId, UserStateType userStateType, String modifyDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("state", userStateType.getStateName());
    params.put("userId", userId);
    params.put("modifyDate", modifyDate);

    return jdbc.update(UserDaoSql.UPDATE_STATE, params);
  }
}
