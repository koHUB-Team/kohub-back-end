package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
  private List<Menu> menus;
}
