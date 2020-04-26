package kr.kohub.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.kohub.dto.Menu;
import kr.kohub.dto.Submenu;
import kr.kohub.dto.response.MenuResponse;
import kr.kohub.exception.MenuNotFoundException;
import kr.kohub.service.MenuService;
import kr.kohub.util.CollectionsUtil;

@RestController
@RequestMapping(path = "/api")
public class kohubApiController {
  @Autowired
  MenuService menuService;

  @CrossOrigin
  @GetMapping(path = "/menus")
  public Map<String, Object> getMenus() {
    List<Menu> menus = menuService.getMenus();
    if (menus == null) {
      throw new MenuNotFoundException();
    }

    for (Menu menu : menus) {
      int menuId = menu.getId();
      List<Submenu> submenus = menuService.getSubmenus(menuId);

      if (submenus != null) {
        menu.setSubmenus(submenus);
      }
    }

    MenuResponse menuResponse = MenuResponse.builder().menus(menus).build();

    return CollectionsUtil.convertObjectToMap(menuResponse);
  }

}
