package kr.kohub.dto.response;

import java.util.List;
import kr.kohub.dto.NoticeBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeBoardResponse {

  @NonNull
  private List<NoticeBoard> items;
  @NonNull
  private int totalNoticeCount;
}
