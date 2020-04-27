package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.AdminMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMenuResponse {
  private List<AdminMenu> menus;
}
