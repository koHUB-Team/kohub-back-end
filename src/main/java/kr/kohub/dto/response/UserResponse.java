package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  @NonNull
  private List<User> users;

  @NonNull
  private int totalCount;

  @NonNull
  private int totalUserCount;

  @NonNull
  private int normalCount;

  @NonNull
  private int warnningCount;

  @NonNull
  private int forbiddenCount;
}
