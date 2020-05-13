package kr.kohub.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kr.kohub.dto.AdminMenu;
import kr.kohub.dto.Menu;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.Submenu;
import kr.kohub.dto.User;
import kr.kohub.dto.param.UserParam;
import kr.kohub.dto.response.AdminMenuResponse;
import kr.kohub.dto.response.MenuResponse;
import kr.kohub.dto.response.NoticeBoardResponse;
import kr.kohub.dto.response.UserResponse;
import kr.kohub.exception.AdminMenuNotFoundException;
import kr.kohub.exception.BadRequestException;
import kr.kohub.exception.MenuNotFoundException;
import kr.kohub.exception.NoticeBoardNotFoundException;
import kr.kohub.exception.UserNotFoundException;
import kr.kohub.service.MenuService;
import kr.kohub.service.NoticeService;
import kr.kohub.service.UserService;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.UserAuthType;
import kr.kohub.type.UserFilterType;
import kr.kohub.type.UserOrderType;
import kr.kohub.type.UserRoleType;
import kr.kohub.type.UserStateType;
import kr.kohub.util.CollectionsUtil;

@RestController
@RequestMapping(path = "/api")
public class kohubApiController {
  @Autowired
  MenuService menuService;

  @Autowired
  UserService userService;

  @Autowired
  NoticeService noticeService;

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
      @RequestParam(name = "start", required = true, defaultValue = "0") int start,
      @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
      @RequestParam(name = "filterType", required = false, defaultValue = "ALL") String filterType,
      @RequestParam(name = "orderType", required = false, defaultValue = "NO") String orderType,
      @RequestParam(name = "orderOption", required = false,
          defaultValue = "ASC") String orderOption) {

    List<User> users = new ArrayList<>();
    int totalCount = 0;
    try {
      switch (UserFilterType.valueOf(filterType)) {
        case ALL:
          users = userService.getUsers(start, UserOrderType.valueOf(orderType),
              OrderOptionType.valueOf(orderOption));
          totalCount = userService.getTotalUserCount();
          break;
        case ROLE:
          users = userService.getUsersByRole(start, UserRoleType.valueOf(filterValue),
              UserOrderType.valueOf(orderType), OrderOptionType.valueOf(orderOption));
          totalCount = userService.getTotalUserCountByRole(UserRoleType.valueOf(filterValue));
          break;
        case AUTH:
          users = userService.getUsersByAuth(start, UserAuthType.valueOf(filterValue),
              UserOrderType.valueOf(orderType), OrderOptionType.valueOf(orderOption));
          totalCount = userService.getTotalUserCountByAuth(UserAuthType.valueOf(filterValue));
          break;
        case STATE:
          users = userService.getUsersByState(start, UserStateType.valueOf(filterValue),
              UserOrderType.valueOf(orderType), OrderOptionType.valueOf(orderOption));
          totalCount = userService.getTotalUserCountByState(UserStateType.valueOf(filterValue));
          break;
      }
    } catch (IllegalArgumentException e) {
      throw new BadRequestException();
    }

    if (users.isEmpty()) {
      throw new UserNotFoundException();
    }

    int totalUserCount = userService.getTotalUserCount();
    int normalCount = userService.getTotalUserCountByState(UserStateType.NORMAL);
    int warnningCount = userService.getTotalUserCountByState(UserStateType.WARRNING);
    int forbiddenCount = userService.getTotalUserCountByState(UserStateType.FORBIDDEN);

    UserResponse userResponse = UserResponse.builder().users(users).totalCount(totalCount)
        .normalCount(normalCount).warnningCount(warnningCount).forbiddenCount(forbiddenCount)
        .totalUserCount(totalUserCount).totalCount(totalCount).build();

    return CollectionsUtil.convertObjectToMap(userResponse);
  }

  @CrossOrigin
  @PutMapping(path = "/admin/users/{userId}/state")
  public Map<String, Object> putUserState(@PathVariable(name = "userId") int userId,
      @RequestBody(required = true) @Valid UserParam userParam) {

    int updateCount = 0;
    try {
      String state = userParam.getState();
      updateCount = userService.changeStateByUserId(userId, UserStateType.valueOf(state));
    } catch (IllegalArgumentException e) {
      throw new BadRequestException();
    }

    if (updateCount == 0) {
      throw new UserNotFoundException();
    }

    return Collections.emptyMap();
  }

  @CrossOrigin
  @GetMapping(path = "/admin/user")
  public Map<String, Object> getUser(
      @RequestParam(name = "email", required = true, defaultValue = "") String email,
      @RequestParam(name = "name", required = true, defaultValue = "") String name) {

    if (!email.equals("") && !name.equals("")) {
      throw new BadRequestException();
    }

    User user = null;
    if (!email.equals("")) {
      user = userService.getUserByEmail(email);
    } else if (!name.equals("")) {
      user = userService.getUserByName(name);
    }

    if (user == null) {
      throw new UserNotFoundException();
    }

    List<User> users = new ArrayList<>();
    users.add(user);

    int totalUserCount = userService.getTotalUserCount();
    int normalCount = userService.getTotalUserCountByState(UserStateType.NORMAL);
    int warnningCount = userService.getTotalUserCountByState(UserStateType.WARRNING);
    int forbiddenCount = userService.getTotalUserCountByState(UserStateType.FORBIDDEN);

    int totalCount = users.size();

    UserResponse userResponse = UserResponse.builder().users(users).totalCount(totalCount)
        .normalCount(normalCount).warnningCount(warnningCount).forbiddenCount(forbiddenCount)
        .totalUserCount(totalUserCount).totalCount(totalCount).build();

    return CollectionsUtil.convertObjectToMap(userResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/notices")
  public Map<String, Object> getNotices(
      @RequestParam(name = "start", required = true, defaultValue = "0") int start) {
    List<NoticeBoard> noticeBoards = noticeService.getNotices(start);

    if (noticeBoards == null) {
      throw new NoticeBoardNotFoundException();
    }

    NoticeBoardResponse noticeBoardResponse =
        NoticeBoardResponse.builder().items(noticeBoards).build();

    return CollectionsUtil.convertObjectToMap(noticeBoardResponse);
  }
}
