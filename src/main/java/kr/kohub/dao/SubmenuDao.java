package kr.kohub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.SubmenuDaoSql;
import kr.kohub.dto.Submenu;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SubmenuDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Submenu> rowMapper = BeanPropertyRowMapper.newInstance(Submenu.class);

  public SubmenuDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Submenu> selectByMenuId(int menuId) {
    List<Submenu> submenus;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("menuId", menuId);

      submenus = jdbc.query(SubmenuDaoSql.SELECT_BY_MENU_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      submenus = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      submenus = null;
    }

    return submenus;
  }
}
