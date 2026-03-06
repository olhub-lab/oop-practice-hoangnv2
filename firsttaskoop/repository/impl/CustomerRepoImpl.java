package firsttaskoop.repository.impl;

import firsttaskoop.model.Customer;
import firsttaskoop.repository.CustomerRepository;
import firsttaskoop.repository.DBConnector;
import firsttaskoop.repository.DBUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomerRepoImpl implements CustomerRepository {
  private static final String INSERT_SQL = "INSERT INTO customer (name, phone_number, account_balance, loyalty_level, address, owner_vehicle) VALUES(?, ?, ?, ?, ?, ?)";
  private static final String SELECT_SQL = "SELECT * FROM customer WHERE id = ?";

  @Override
  public void insert(Customer cus) throws SQLException {

    Connection conn = null;

    try {
      conn = DBConnector.getInstance().getConnection();
      conn.setAutoCommit(false);

      try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
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

  @Override
  public void updateCustomerSelective(int id, Map<String, Object> fieldsToUpdate)
      throws SQLException {
    if (fieldsToUpdate == null || fieldsToUpdate.isEmpty()) {
      return;
    }

    StringBuilder sql = new StringBuilder("UPDATE customer SET ");
    List<Object> values = new ArrayList<>();

    fieldsToUpdate.forEach((column, value) -> {
      sql.append(column).append(" = ?, ");
      values.add(value);
    });

    sql.setLength(sql.length() - 2);
    sql.append(" WHERE id = ?");
    values.add(id);

    try (Connection conn = DBConnector.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString())) {

      for (int i = 0; i < values.size(); i++) {
        ps.setObject(i + 1, values.get(i));
      }

      ps.executeUpdate();
    }
  }

  @Override
  public Customer getCustomerById(int id) throws SQLException {

    try (Connection conn = DBConnector.getInstance().getConnection();) {
      PreparedStatement ps = conn.prepareStatement(SELECT_SQL);

      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String name = rs.getString("name");
          String phoneNumber = rs.getString("phone_number");
          BigDecimal balance = rs.getBigDecimal("account_balance");
          String address = rs.getString("address");

          Customer cus = new Customer(name, phoneNumber, address, balance);
          return cus;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}
