package kr.kohub.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.MenuDaoSql;
import kr.kohub.dto.Menu;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MenuDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Menu> rowMapper = BeanPropertyRowMapper.newInstance(Menu.class);

  public MenuDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Menu> selectAll() {
    List<Menu> menus;

    try {
      menus = jdbc.query(MenuDaoSql.SELECT_ALL, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      menus = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      menus = null;
    }

    return menus;
  }
}
