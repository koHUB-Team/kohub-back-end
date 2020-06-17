package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.FreeBoard;
import kr.kohub.dto.FreeBoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeBoardResponse {

  private List<FreeBoard> freeBoards;

  private int totalCount;

  private FreeBoard freeBoard;

  private List<FreeBoardComment> comments;

  private int totalCommentCount;
}
