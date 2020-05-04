package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.UserDao;
import kr.kohub.dto.User;
import kr.kohub.service.UserService;
import kr.kohub.type.OrderOptionType;
import kr.kohub.type.UserAuthType;
import kr.kohub.type.UserOrderType;
import kr.kohub.type.UserRoleType;
import kr.kohub.type.UserStateType;
import kr.kohub.util.DateUtil;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserDao userDao;

  @Transactional(readOnly = true)
  @Override
  public List<User> getUsers(int start, UserOrderType userOrderType,
      OrderOptionType orderOptionType) {
    return userDao.selectPaging(start, userOrderType, orderOptionType);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getUsersByRole(int start, UserRoleType userRoleType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    return userDao.selectPagingByRole(start, userRoleType, userOrderType, orderOptionType);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getUsersByAuth(int start, UserAuthType userAuthType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    return userDao.selectPagingByAuth(start, userAuthType, userOrderType, orderOptionType);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getUsersByState(int start, UserStateType userStateType,
      UserOrderType userOrderType, OrderOptionType orderOptionType) {
    return userDao.selectPagingByState(start, userStateType, userOrderType, orderOptionType);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalUserCount() {
    return userDao.selectTotalUserCount();
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalUserCountByRole(UserRoleType userRoleType) {
    return userDao.selectTotalUserCountByRole(userRoleType);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalUserCountByAuth(UserAuthType userAuthType) {
    return userDao.selectTotalUserCountByAuth(userAuthType);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalUserCountByState(UserStateType userStateType) {
    return userDao.selectTotalUserCountByState(userStateType);
  }

  @Transactional(readOnly = true)
  @Override
  public User getUserByEmail(String email) {
    return userDao.selectByEmail(email);
  }

  @Transactional(readOnly = true)
  @Override
  public User getUserByName(String name) {
    return userDao.selectByName(name);
  }

  @Transactional(readOnly = false)
  @Override
  public int changeStateByUserId(int userId, UserStateType userStateType) {
    String modifyDate = DateUtil.getNowDate();
    return userDao.updateState(userId, userStateType, modifyDate);
  }
}
