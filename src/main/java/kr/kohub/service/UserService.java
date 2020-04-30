package kr.kohub.service;

import java.util.List;
import kr.kohub.dto.User;

public interface UserService {
  public List<User> getUsers(int start);

  public int getTotalCount();
}
