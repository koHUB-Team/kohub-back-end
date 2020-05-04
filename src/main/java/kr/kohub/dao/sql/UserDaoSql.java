package kr.kohub.dao.sql;

public class UserDaoSql {
  public static final int LIMIT = 10;

  public static final String SELECT_PAGING =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_PAGING_BY_ROLE =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user WHERE role = :userRoleType ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_PAGING_BY_AUTH =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user WHERE auth = :userAuthType ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_PAGING_BY_STATE =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user WHERE state = :userStateType ORDER BY %s %s LIMIT :start, :limit";

  public static final String SELECT_TOTAL_USER_COUNT = "SELECT count(*) FROM user";

  public static final String SELECT_TOTAL_USER_COUNT_BY_ROLE =
      "SELECT count(*) FROM user WHERE role = :userRoleType";

  public static final String SELECT_TOTAL_USER_COUNT_BY_AUTH =
      "SELECT count(*) FROM user WHERE auth = :userAuthType";

  public static final String SELECT_TOTAL_USER_COUNT_BY_STATE =
      "SELECT count(*) FROM user WHERE state = :userStateType";

  public static final String SELECT_BY_EMAIL =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user WHERE email = :email";

  public static final String SELECT_BY_NAME =
      "SELECT id, name, email, auth, state, role, create_date, modify_date FROM user WHERE name = :name";

  public static final String UPDATE_STATE =
      "UPDATE user SET state=:state, modify_date=:modifyDate WHERE id=:userId";
}
