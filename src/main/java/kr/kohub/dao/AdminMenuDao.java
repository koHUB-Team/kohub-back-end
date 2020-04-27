package kr.kohub.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.AdminMenuDaoSql;
import kr.kohub.dto.AdminMenu;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AdminMenuDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<AdminMenu> rowMapper = BeanPropertyRowMapper.newInstance(AdminMenu.class);

  public AdminMenuDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<AdminMenu> selectAll() {
    List<AdminMenu> adminMenus;
    try {
      adminMenus = jdbc.query(AdminMenuDaoSql.SELECT_ALL, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      adminMenus = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      adminMenus = null;
    }
    return adminMenus;
  }
}
