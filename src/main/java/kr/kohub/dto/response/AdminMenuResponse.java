package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.AdminMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMenuResponse {
  @NonNull
  private List<AdminMenu> menus;
}