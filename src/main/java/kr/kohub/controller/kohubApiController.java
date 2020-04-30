package kr.kohub.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kr.kohub.dto.AdminMenu;
import kr.kohub.dto.Menu;
import kr.kohub.dto.Submenu;
import kr.kohub.dto.User;
import kr.kohub.dto.response.AdminMenuResponse;
import kr.kohub.dto.response.MenuResponse;
import kr.kohub.dto.response.UserResponse;
import kr.kohub.exception.AdminMenuNotFoundException;
import kr.kohub.exception.MenuNotFoundException;
import kr.kohub.exception.UserNotFoundException;
import kr.kohub.service.MenuService;
import kr.kohub.service.UserService;
import kr.kohub.util.CollectionsUtil;

@RestController
@RequestMapping(path = "/api")
public class kohubApiController {
  @Autowired
  MenuService menuService;

  @Autowired
  UserService userService;

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


  @CrossOrigin
  @GetMapping(path = "/admin/menus")
  public Map<String, Object> getAdminMenus() {
    List<AdminMenu> adminMenus = menuService.getAdminMenus();
    if (adminMenus == null) {
      throw new AdminMenuNotFoundException();
    }

    AdminMenuResponse adminMenuResponse = AdminMenuResponse.builder().menus(adminMenus).build();

    return CollectionsUtil.convertObjectToMap(adminMenuResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/admin/users")
  public Map<String, Object> getUsers(
      @RequestParam(required = true, defaultValue = "0") int start) {
    List<User> users = userService.getUsers(start);
    if (users == null) {
      throw new UserNotFoundException();
    }

    int totalCount = userService.getTotalCount();

    UserResponse userResponse = UserResponse.builder().users(users).totalCount(totalCount).build();

    return CollectionsUtil.convertObjectToMap(userResponse);
  }
}
