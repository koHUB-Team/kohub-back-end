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
import kr.kohub.dto.Faq;
import kr.kohub.dto.FreeBoard;
import kr.kohub.dto.FreeBoardComment;
import kr.kohub.dto.Menu;
import kr.kohub.dto.NoticeBoard;
import kr.kohub.dto.Promotion;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.dto.Qna;
import kr.kohub.dto.QnaComment;
import kr.kohub.dto.Submenu;
import kr.kohub.dto.User;
import kr.kohub.dto.param.FaqParam;
import kr.kohub.dto.param.FreeBoardCommentParam;
import kr.kohub.dto.param.FreeBoardParam;
import kr.kohub.dto.param.NoticeBoardParam;
import kr.kohub.dto.param.PromotionParam;
import kr.kohub.dto.param.PromotionStateParam;
import kr.kohub.dto.param.QnaParam;
import kr.kohub.dto.param.UserParam;
import kr.kohub.dto.response.AdminMenuResponse;
import kr.kohub.dto.response.FaqResponse;
import kr.kohub.dto.response.FreeBoardResponse;
import kr.kohub.dto.response.MenuResponse;
import kr.kohub.dto.response.NoticeBoardResponse;
import kr.kohub.dto.response.PromotionResponse;
import kr.kohub.dto.response.QnaResponse;
import kr.kohub.dto.response.UserResponse;
import kr.kohub.exception.AdminMenuNotFoundException;
import kr.kohub.exception.BadRequestException;
import kr.kohub.exception.FaqNotFoundException;
import kr.kohub.exception.FileInfoNotFoundException;
import kr.kohub.exception.FreeBoardNotFoundException;
import kr.kohub.exception.MenuNotFoundException;
import kr.kohub.exception.NoticeBoardNotFoundException;
import kr.kohub.exception.PromotionNotFoundException;
import kr.kohub.exception.QnaNotFoundException;
import kr.kohub.exception.UserNotFoundException;
import kr.kohub.service.FaqService;
import kr.kohub.service.FreeBoardService;
import kr.kohub.service.MenuService;
import kr.kohub.service.NoticeService;
import kr.kohub.service.PromotionService;
import kr.kohub.service.QnaService;
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
  FaqService faqService;

  @Autowired
  QnaService qnaService;

  @Autowired
  FreeBoardService freeBoardService;

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
    NoticeBoardResponse noticeBoardResponse = NoticeBoardResponse.builder().items(noticeBoards)
        .totalNoticeCount(totalNoticeCount).build();

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

  @CrossOrigin
  @DeleteMapping(path = "/notices/{noticeId}")
  public Map<String, Object> deleteNotice(
      @PathVariable(name = "noticeId", required = true) int noticeId) {

    // 삭제 API 보안문제 이슈

    NoticeBoard noticeBoard = noticeService.getNotice(noticeId);
    if (noticeBoard == null) {
      throw new BadRequestException();
    }


    noticeService.removeNotice(noticeId);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/notices/{noticeId}")
  public Map<String, Object> putNotice(@PathVariable(name = "noticeId") int noticeId,
      @RequestBody(required = true) @Valid NoticeBoardParam noticeBoardParam) {
    NoticeBoard noticeBoard = noticeService.getNotice(noticeId);
    if (noticeBoard == null) {
      throw new BadRequestException();
    }

    String title = noticeBoardParam.getTitle();
    String content = noticeBoardParam.getContent();

    noticeService.changeNotice(noticeId, title, content);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @GetMapping(path = "/faqs")
  public Map<String, Object> getFaqs(
      @RequestParam(name = "start", required = true, defaultValue = "0") int start) {

    List<Faq> faqs = faqService.getFaqs(start);
    if (faqs == null) {
      throw new FaqNotFoundException();
    }
    int totalFaqCount = faqService.getTotalFaqCount();
    FaqResponse faqResponse = FaqResponse.builder().faqs(faqs).totalFaqCount(totalFaqCount).build();

    return CollectionsUtil.convertObjectToMap(faqResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/admin/faqs/{faqId}")
  public Map<String, Object> getFaq(@PathVariable(required = true, name = "faqId") int faqId) {
    Faq faq = faqService.getFaqById(faqId);

    if (faq == null) {
      throw new FaqNotFoundException();
    }

    return CollectionsUtil.convertObjectToMap(faq);
  }

  @CrossOrigin
  @PostMapping(path = "/faq")
  public Map<String, Object> postFaq(@RequestBody(required = true) @Valid FaqParam faqParam) {
    faqService.addFaq(faqParam);
    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/admin/faqs/{faqId}")
  public Map<String, Object> putFaq(@PathVariable(required = true, name = "faqId") int faqId,
      @RequestBody(required = true) @Valid FaqParam faqParam) {
    faqService.changeFaqById(faqId, faqParam);
    return Collections.emptyMap();
  }

  @CrossOrigin
  @DeleteMapping(path = "/admin/faqs/{faqId}")
  public Map<String, Object> deleteFaq(@PathVariable(required = true, name = "faqId") int faqId) {
    int deleteCount = faqService.removeByFaqId(faqId);

    if (deleteCount == 0) {
      throw new FaqNotFoundException();
    }

    return Collections.emptyMap();
  }

  @CrossOrigin
  @GetMapping(path = "/qnas")
  public Map<String, Object> getQnas(
      @RequestParam(name = "start", required = true, defaultValue = "0") int start) {

    List<Qna> qnas = qnaService.getQnas(start);
    if (qnas == null) {
      throw new QnaNotFoundException();
    }
    int totalQnaCount = qnaService.getTotalQnaCount();
    QnaResponse qnaResponse = QnaResponse.builder().qnas(qnas).totalQnaCount(totalQnaCount).build();
    return CollectionsUtil.convertObjectToMap(qnaResponse);
  }


  /// ???search qna는 이상.
  @CrossOrigin
  @GetMapping(path = "/qnas/search")
  public Map<String, Object> searchQna(
      @RequestParam(name = "title", required = true, defaultValue = "") String title,
      @RequestParam(name = "userName", required = true, defaultValue = "") String userName) {

    List<Qna> qnas = null;
    int totalQnaCount = 0;
    if (title.equals("")) {
      qnas = qnaService.getQnasByName(userName);
      totalQnaCount = qnas.size();
    } else if (userName.equals("")) {
      qnas = qnaService.getQnasByTitle(title);
      totalQnaCount = qnas.size();
    }

    QnaResponse qnaResponse = QnaResponse.builder().qnas(qnas).totalQnaCount(totalQnaCount).build();
    return CollectionsUtil.convertObjectToMap(qnaResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/qna")
  public Map<String, Object> getQna(@RequestParam(name = "qnaId", required = true) int qnaId) {
    Qna qna = qnaService.getQna(qnaId);
    QnaComment qnaComment = qnaService.getComment(qnaId);

    QnaResponse qnaResponse = null;

    if (qna == null) {
      throw new QnaNotFoundException();
    }
    if (qnaComment == null) {
      qnaResponse = QnaResponse.builder().qna(qna).build();
    } else {
      qnaResponse = QnaResponse.builder().qna(qna).qnaComment(qnaComment).build();
    }

    return CollectionsUtil.convertObjectToMap(qnaResponse);
  }

  @CrossOrigin
  @PostMapping(path = "/qna")
  public Map<String, Object> postQna(@RequestBody(required = true) @Valid QnaParam qnaParam) {
    qnaService.addQna(qnaParam);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @DeleteMapping(path = "/qnas/{qnaId}")
  public Map<String, Object> deleteQna(@PathVariable(name = "qnaId", required = true) int qnaId) {
    Qna qna = qnaService.getQna(qnaId);
    if (qna == null) {
      throw new BadRequestException();
    }
    qnaService.removeQna(qnaId);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/qnas/{qnaId}")
  public Map<String, Object> putQna(@PathVariable(name = "qnaId") int qnaId,
      @RequestBody(required = true) @Valid QnaParam qnaParam) {
    Qna qna = qnaService.getQna(qnaId);
    if (qna == null) {
      throw new BadRequestException();
    }

    String title = qnaParam.getTitle();
    String content = qnaParam.getContent();
    String category = qnaParam.getCategory();

    qnaService.changeQna(qnaId, title, content, category);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @GetMapping(path = "/frees")
  public Map<String, Object> getFrees(
      @RequestParam(name = "start", required = true, defaultValue = "0") int start) {

    List<FreeBoard> freeBoards = freeBoardService.getFreeBoards(start);
    if (freeBoards == null) {
      throw new FreeBoardNotFoundException();
    }
    int totalCount = freeBoardService.getTotalFreeBoardCount();
    FreeBoardResponse freeBoardResponse =
        FreeBoardResponse.builder().freeBoards(freeBoards).totalCount(totalCount).build();
    return CollectionsUtil.convertObjectToMap(freeBoardResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/frees/search")
  public Map<String, Object> searchFrees(
      @RequestParam(name = "title", required = true, defaultValue = "") String title,
      @RequestParam(name = "userName", required = true, defaultValue = "") String userName) {

    List<FreeBoard> frees = null;
    int totalCount = 0;
    if (title.equals("")) {
      frees = freeBoardService.getFreeBoardsByName(userName);
      totalCount = frees.size();
    } else if (userName.equals("")) {
      frees = freeBoardService.getFreeBoardsByTitle(title);
      totalCount = frees.size();
    }

    FreeBoardResponse freeBoardResponse =
        FreeBoardResponse.builder().freeBoards(frees).totalCount(totalCount).build();
    return CollectionsUtil.convertObjectToMap(freeBoardResponse);
  }

  @CrossOrigin
  @GetMapping(path = "/free")
  public Map<String, Object> getFree(@RequestParam(name = "freeId", required = true) int freeId) {
    FreeBoard freeBoard = freeBoardService.getFreeBoard(freeId);
    List<FreeBoardComment> comments = freeBoardService.getComments(freeId);
    int totalCommentCount = 0;
    FreeBoardResponse freeBoardResponse = null;

    if (freeBoard == null) {
      throw new FreeBoardNotFoundException();
    }

    if (comments == null) {
      freeBoardResponse = FreeBoardResponse.builder().freeBoard(freeBoard).build();
    } else {
      totalCommentCount = freeBoardService.getTotalCommentCount(freeId);
      freeBoardResponse = FreeBoardResponse.builder().freeBoard(freeBoard).comments(comments)
          .totalCommentCount(totalCommentCount).build();
    }

    return CollectionsUtil.convertObjectToMap(freeBoardResponse);
  }

  @CrossOrigin
  @PostMapping(path = "/free")
  public Map<String, Object> postFree(
      @RequestBody(required = true) @Valid FreeBoardParam freeBoardParam) {
    freeBoardService.addFreeBoard(freeBoardParam);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @DeleteMapping(path = "/free/{freeId}")
  public Map<String, Object> deleteFree(
      @PathVariable(name = "freeId", required = true) int freeId) {
    FreeBoard freeBoard = freeBoardService.getFreeBoard(freeId);
    if (freeBoard == null) {
      throw new BadRequestException();
    }
    freeBoardService.removeFree(freeId);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/free/{freeId}")
  public Map<String, Object> putFree(@PathVariable(name = "freeId") int freeId,
      @RequestBody(required = true) @Valid FreeBoardParam freeBoardParam) {
    FreeBoard freeBoard = freeBoardService.getFreeBoard(freeId);
    if (freeBoard == null) {
      throw new BadRequestException();
    }

    String title = freeBoardParam.getTitle();
    String content = freeBoardParam.getContent();

    freeBoardService.changeFree(freeId, title, content);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PostMapping(path = "/free/comment")
  public Map<String, Object> postFreeComment(
      @RequestBody(required = true) @Valid FreeBoardCommentParam freeBoardCommentParam) {
    freeBoardService.addComment(freeBoardCommentParam);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @DeleteMapping(path = "/free/comment/{commentId}")
  public Map<String, Object> deleteFreeComment(
      @PathVariable(name = "commentId", required = true) int commentId) {
    freeBoardService.removeComment(commentId);

    return Collections.emptyMap();
  }

  @CrossOrigin
  @PutMapping(path = "/free/comment/{commentId}")
  public Map<String, Object> putFreeComment(@PathVariable(name = "commentId") int commentId,
      @RequestBody(required = true) @Valid FreeBoardCommentParam freeBoardCommentParam) {

    String comment = freeBoardCommentParam.getComment();

    freeBoardService.changeComment(commentId, comment);

    return Collections.emptyMap();
  }
}

