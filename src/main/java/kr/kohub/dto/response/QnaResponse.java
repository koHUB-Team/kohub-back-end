package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.Qna;
import kr.kohub.dto.QnaComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaResponse {

  private List<Qna> qnas;

  private int totalQnaCount;

  private Qna qna;

  private QnaComment qnaComment;
}
