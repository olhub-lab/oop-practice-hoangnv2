package firsttaskoop.repository;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

  public static void rollback(Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
        System.err.println("Dữ liệu đã được khôi phục (Rollback) do có lỗi!");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void close(Connection conn) {
    if (conn != null) {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}