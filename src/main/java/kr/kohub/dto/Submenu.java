package kr.kohub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submenu {
  @NonNull
  private int id;

  @NonNull
  private String title;

  @NonNull
  private int menuId;
}
