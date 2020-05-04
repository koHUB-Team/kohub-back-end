package kr.kohub.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static String getNowDate() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  }
}
