package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.AdminMenu;
import kr.kohub.dto.Menu;
import kr.kohub.dto.Submenu;

public interface MenuService {
  public List<Menu> getMenus();

  public List<Submenu> getSubmenus(int menuId);

  public List<AdminMenu> getAdminMenus();
}
