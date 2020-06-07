package kr.kohub.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kr.kohub.dto.AdminMenu;
import kr.kohub.dto.Menu;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.dto.Submenu;
import kr.kohub.dto.User;
import kr.kohub.dto.param.NoticeBoardParam;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.dto.param.PromotionStateParam;
import kr.kohub.dto.param.UserParam;
import kr.kohub.dto.response.AdminMenuResponse;
import kr.kohub.dto.response.MenuResponse;
import kr.kohub.dto.response.NoticeBoardResponse;
import kr.kohub.dto.response.PromotionResponse;
import kr.kohub.dto.response.UserResponse;
import kr.kohub.exception.AdminMenuNotFoundException;
import kr.kohub.exception.BadRequestException;
import kr.kohub.exception.FileInfoNotFoundException;
import kr.kohub.exception.MenuNotFoundException;
import kr.kohub.exception.NoticeBoardNotFoundException;
import kr.kohub.exception.PromotionNotFoundException;
import kr.kohub.exception.UserNotFoundException;
import kr.kohub.service.MenuService;
import kr.kohub.service.NoticeService;
import kr.kohub.service.PromotionService;
import kr.kohub.service.UserService;
import kr.kohub.type.ImageFileExtensionType;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.PromotionFilterType;
import kr.kohub.type.PromotionOrderType;
import kr.kohub.type.PromotionStateType;
import kr.kohub.type.UserAuthType;
import kr.kohub.type.UserFilterType;
import kr.kohub.type.UserOrderType;
import kr.kohub.type.UserRoleType;
import kr.kohub.type.UserStateType;
import kr.kohub.util.CollectionsUtil;
import kr.kohub.util.FileUtil;

// 이름변경 대문자로
@RestController
@RequestMapping(path = "/api")
public class kohubApiController {
  @Autowired
  MenuService menuService;

  @Autowired
  UserService userService;

  @Autowired
  NoticeService noticeService;

  @Autowired
  PromotionService promotionService;

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
  @GetMapping(path = "/admin/promotions")
  public Map<String, Object> getPromotions(
      @RequestParam(name = "start", defaultValue = "0", required = true) int start,
      @RequestParam(name = "orderType", defaultValue = "NO", required = false) String orderType,
      @RequestParam(name = "orderOption", defaultValue = "ASC",
          required = false) String orderOption,
      @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
      @RequestParam(name = "filterType", required = false,
          defaultValue = "ALL") String filterType) {

    List<Promotion> promotions;
    int totalCount;
    try {
      switch (PromotionFilterType.valueOf(filterType)) {
        case ALL:
          promotions = promotionService.getPromotions(start, PromotionOrderType.valueOf(orderType),
              OrderOptionType.valueOf(orderOption));
          totalCount = promotionService.getTotalPromotionCount();
          break;
        case STATE:
          promotions =
              promotionService.getPromotionsByState(start, PromotionStateType.valueOf(filterValue),
                  PromotionOrderType.valueOf(orderType), OrderOptionType.valueOf(orderOption));
          totalCount = promotionService
              .getTotalPromotionCountByState(PromotionStateType.valueOf(filterValue));
          break;

        default:
          throw new BadRequestException();
      }

    } catch (IllegalArgumentException e) {
      throw new BadRequestException();
    }

    if (promotions == null) {
      throw new PromotionNotFoundException();
    }

    List<PromotionFileInfo> promotionImages = new ArrayList<>();
    for (Promotion promotion : promotions) {
      int promotionId = promotion.getId();
      List<PromotionFileInfo> promotionFileInfos = promotionService.getPromotionImages(promotionId);

      if (promotionFileInfos == null) {
        throw new FileInfoNotFoundException();
      }

      for (PromotionFileInfo promotionFileInfo : promotionFileInfos) {
        promotionImages.add(promotionFileInfo);
      }
    }

    int totalPromotionCount = promotionService.getTotalPromotionCount();
    int totalPromotingCount =
        promotionService.getTotalPromotionCountByState(PromotionStateType.PROMOTING);
    int totalWaitingCount =
        promotionService.getTotalPromotionCountByState(PromotionStateType.WAITING);

    PromotionResponse promotionResponse =
        PromotionResponse.builder().promotions(promotions).totalCount(totalCount)
            .totalPromotionCount(totalPromotionCount).totalPromotingCount(totalPromotingCount)
            .totalWaitingCount(totalWaitingCount).promotionImages(promotionImages).build();

    return CollectionsUtil.convertObjectToMap(promotionResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/admin/promotions/{promotionId}")
  public Map<String, Object> getPromotion(
      @PathVariable(required = true, name = "promotionId") int promotionId) {

    Promotion promotion = promotionService.getPromotionById(promotionId);
    if (promotion == null) {
      throw new PromotionNotFoundException();
    }

    List<PromotionFileInfo> promotionImages = promotionService.getPromotionImages(promotionId);
    if (promotionImages == null) {
      throw new FileInfoNotFoundException();
    }

    List<Promotion> promotions = new ArrayList<>();
    promotions.add(promotion);

    PromotionResponse promotionResponse =
        PromotionResponse.builder().promotions(promotions).promotionImages(promotionImages).build();

    return CollectionsUtil.convertObjectToMap(promotionResponse);
  }

  @CrossOrigin
  @PostMapping(path = "/admin/promotion")
  public Map<String, Object> postPromotion(@ModelAttribute @Valid PromotionParam promotionParam) {

    // date검사도 필요
    try {
      String fileName = promotionParam.getPromotionImage().getOriginalFilename();
      String fileExtension = FileUtil.getFileExtension(fileName);
      ImageFileExtensionType.valueOf(fileExtension.toUpperCase());
    } catch (Exception e) {
      throw new BadRequestException();
    }

    String email = promotionParam.getEmail();
    User user = userService.getUserByEmail(email);
    if (user == null) {
      throw new UserNotFoundException();
    }

    int userId = user.getId();
    promotionService.addPromotion(promotionParam, userId);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/admin/promotions/{promotionId}/state")
  public Map<String, Object> putPromotionState(@PathVariable(name = "promotionId") int promotionId,
      @RequestBody(required = true) @Valid PromotionStateParam promotionStateParam) {
    int updateCount = 0;
    try {
      String state = promotionStateParam.getState();
      updateCount =
          promotionService.changeStateByPromotionId(promotionId, PromotionStateType.valueOf(state));
    } catch (IllegalArgumentException e) {
      throw new BadRequestException();
    }

    if (updateCount == 0) {
      throw new PromotionNotFoundException();
    }

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/admin/promotions/{promotionId}")
  public Map<String, Object> putPromotion(@ModelAttribute @Valid PromotionParam promotionParam,
      @PathVariable(name = "promotionId") int promotionId) {
    promotionService.changePromotionByPromotionId(promotionId, promotionParam);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @DeleteMapping(path = "/admin/promotions/{promotionId}")
  public Map<String, Object> deletePromotion(
      @PathVariable(name = "promotionId", required = true) int promotionId) {
    int deleteCount = 0;
    deleteCount = promotionService.removePromotionById(promotionId);

    if (deleteCount == 0) {
      throw new PromotionNotFoundException();
    }

    return Collections.emptyMap();
  }

  @CrossOrigin
  @GetMapping(path = "/notices")
  public Map<String, Object> getNotices(
      @RequestParam(name = "start", required = true, defaultValue = "0") int start) {

    List<NoticeBoard> noticeBoards = noticeService.getNotices(start);
    if (noticeBoards == null) {
      throw new NoticeBoardNotFoundException();
    }
    int totalNoticeCount = noticeService.getTotalNoticeCount();
    NoticeBoard noticeBoard = noticeService.getNotice(0);
    NoticeBoardResponse noticeBoardResponse = NoticeBoardResponse.builder().items(noticeBoards)
        .totalNoticeCount(totalNoticeCount).noticeBoard(noticeBoard).build();

    return CollectionsUtil.convertObjectToMap(noticeBoardResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/notice")
  public Map<String, Object> getNotice(
      @RequestParam(name = "noticeId", required = true) int noticeId) {
    NoticeBoard noticeBoard = noticeService.getNotice(noticeId);
    if (noticeBoard == null) {
      throw new NoticeBoardNotFoundException();
    }

    return CollectionsUtil.convertObjectToMap(noticeBoard);
  }

  @CrossOrigin
  @PostMapping(path = "/notice")
  public Map<String, Object> postNotice(
      @RequestBody(required = true) @Valid NoticeBoardParam noticeBoardParam) {
    noticeService.addNotice(noticeBoardParam);

    return Collections.emptyMap();
  }
}
