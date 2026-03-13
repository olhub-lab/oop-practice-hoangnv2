package firsttaskoop.repository;

import firsttaskoop.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

  public static void rollback(Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
      } catch (SQLException e) {
        throw new DataAccessException("Lỗi rồi, đã rollback: " + e.getMessage());
      }
    }
  }

  public static void close(Connection conn) {
    if (conn != null) {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (SQLException e) {
        throw new DataAccessException("Lỗi rồi ko đóng đươc :(( " + e.getMessage());
      }
    }
  }
}