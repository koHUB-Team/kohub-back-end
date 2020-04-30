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
public class User {
  private int id;

  @NonNull
  private String name;

  @NonNull
  private String email;

  @NonNull
  @Builder.Default
  private boolean auth = false;

  @NonNull
  private String state;

  @NonNull
  private String role;

  private String createDate;
  private String modifyDate;
}
