package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.User;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.UserAuthType;
import kr.kohub.type.UserOrderType;
import kr.kohub.type.UserRoleType;
import kr.kohub.type.UserStateType;

public interface UserService {
  public List<User> getUsers(int start, UserOrderType userOrderType,
      OrderOptionType orderOptionType);

  public List<User> getUsersByRole(int start, UserRoleType userRoleType,
      UserOrderType userOrderType, OrderOptionType orderOptionType);

  public List<User> getUsersByAuth(int start, UserAuthType userAuthType,
      UserOrderType userOrderType, OrderOptionType orderOptionType);

  public List<User> getUsersByState(int start, UserStateType userStateType,
      UserOrderType userOrderType, OrderOptionType orderOptionType);

  public int getTotalUserCount();

  public int getTotalUserCountByRole(UserRoleType userRoleType);

  public int getTotalUserCountByAuth(UserAuthType userAuthType);

  public int getTotalUserCountByState(UserStateType userStateType);

  public User getUserByEmail(String email);

  public User getUserByName(String name);

  public int changeStateByUserId(int userId, UserStateType userStateType);
}
