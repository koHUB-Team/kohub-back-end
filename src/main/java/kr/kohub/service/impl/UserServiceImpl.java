package kr.kohub.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.kohub.dao.UserDao;
import kr.kohub.dto.User;
import kr.kohub.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserDao userDao;

  @Transactional(readOnly = true)
  @Override
  public List<User> getUsers(int start) {
    return userDao.selectPaging(start);
  }

  @Transactional(readOnly = true)
  @Override
  public int getTotalCount() {
    return userDao.selectTotalCount();
  }

}
