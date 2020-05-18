package kr.kohub.dto.response;

import kr.kohub.dto.NoticeBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeResponse {

  private NoticeBoard noticeBoard;

}
