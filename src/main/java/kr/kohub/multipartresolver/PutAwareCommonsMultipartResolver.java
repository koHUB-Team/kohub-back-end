package kr.kohub.multipartresolver;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class PutAwareCommonsMultipartResolver extends CommonsMultipartResolver {
  private static final String MULTIPART = "multipart/";

  @Override
  public boolean isMultipart(HttpServletRequest request) {
    return request != null && isMultipartContent(request);
  }

  public static final boolean isMultipartContent(HttpServletRequest request) {
    final String method = request.getMethod().toLowerCase();
    if (!method.equals("post") && !method.equals("put")) {
      return false;
    }
    String contentType = request.getContentType();
    if (contentType == null) {
      return false;
    }
    if (contentType.toLowerCase().startsWith(MULTIPART)) {
      return true;
    }
    return false;
  }
}
