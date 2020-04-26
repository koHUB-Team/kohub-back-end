package kr.kohub.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
  @NonNull
  private int id;

  @NonNull
  private String title;

  private List<Submenu> submenus;
}
