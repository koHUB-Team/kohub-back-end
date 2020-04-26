package kr.kohub.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    logger.debug("Method : {} / URL : {} / IP : {}", request.getMethod(), request.getRequestURL(),
        request.getRemoteAddr());
    logger.debug("{} 를 호출했습니다.", handler.toString());

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    if (modelAndView != null) {
      logger.debug("{} 가 종료되었습니다. {} 를  사용합니다.", handler.toString(), modelAndView.getViewName());
    } else {
      logger.debug("{} 가 종료되었습니다.", handler.toString());
    }

  }
}
