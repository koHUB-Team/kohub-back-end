package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Qna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaResponse {

  @NonNull
  private List<Qna> qnas;

  private int totalQnaCount;
}
