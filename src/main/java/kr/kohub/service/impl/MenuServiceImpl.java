package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.AdminMenuDao;
import kr.kohub.dao.MenuDao;
import kr.kohub.dao.SubmenuDao;
import kr.kohub.dto.AdminMenu;
import kr.kohub.dto.Menu;
import kr.kohub.dto.Submenu;
import kr.kohub.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
  @Autowired
  MenuDao menuDao;

  @Autowired
  SubmenuDao submenuDao;

  @Autowired
  AdminMenuDao adminMenuDao;

  @Transactional(readOnly = true)
  @Override
  public List<Menu> getMenus() {
    return menuDao.selectAll();
  }

  @Transactional(readOnly = true)
  @Override
  public List<Submenu> getSubmenus(int menuId) {
    return submenuDao.selectByMenuId(menuId);
  }

  @Transactional(readOnly = true)
  @Override
  public List<AdminMenu> getAdminMenus() {
    return adminMenuDao.selectAll();
  }
}
