package kr.kohub.dao.sql;

public class SubmenuDaoSql {
  public static final String SELECT_BY_MENU_ID =
      "SELECT id, title, menu_id FROM submenu WHERE menu_id = :menuId ORDER BY id";
}
