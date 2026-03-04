package firsttaskoop.repository.impl;

import firsttaskoop.model.Customer;
import firsttaskoop.repository.CustomerRepository;
import firsttaskoop.repository.DBConnector;
import firsttaskoop.repository.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CustomerRepoImpl implements CustomerRepository {

  @Override
  public void insert(Customer cus) throws SQLException {
    String sql = "INSERT INTO customer (name, phone_number, account_balance, loyalty_level, address, owner_vehicle) VALUES(?, ?, ?, ?, ?, ?)";
    Connection conn = null;

    try {
      conn = DBConnector.getInstance().getConnection();
      conn.setAutoCommit(false);

      try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, cus.getName());
        ps.setString(2, cus.getPhoneNumber());
        ps.setBigDecimal(3, cus.getAccountBalance());
        ps.setString(4, cus.getLoyaltyLevel().name());
        ps.setString(5, cus.getAddress());
        ps.setInt(6, cus.getOwnerVehicle());

        ps.executeUpdate();
        conn.commit();
        System.out.println("Đã lưu dữ liệu người dùng " + cus.getName() + " vào database!!");
      } catch (SQLException e) {
        DBUtils.rollback(conn);
        throw e;
      }
    } finally {
      DBUtils.close(conn);
    }
  }

}
