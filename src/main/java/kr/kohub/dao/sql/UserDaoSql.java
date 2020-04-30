package kr.kohub.dao.sql;

public class UserDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user LIMIT :start, :limit";

  public static final String SELECT_TOTAL_COUNT = "SELECT count(*) AS totalCount FROM user";
}
