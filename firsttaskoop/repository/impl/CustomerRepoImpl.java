package firsttaskoop.repository.impl;

import firsttaskoop.model.Customer;
import firsttaskoop.repository.CustomerRepository;
import firsttaskoop.repository.DBConnector;
import firsttaskoop.repository.query.CustomerQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomerRepoImpl implements CustomerRepository {

  @Override
  public void insert(Customer cus) {
    try (Connection conn = DBConnector.getInstance().getConnection()) {
      try {
        conn.setAutoCommit(false);

        try (PreparedStatement ps = conn.prepareStatement(CustomerQueries.INSERT_SQL)) {
          Object[] values = {
              cus.getName(),
              cus.getPhoneNumber(),
              cus.getAccountBalance(),
              cus.getLoyaltyLevel().name(),
              cus.getAddress(),
              cus.getOwnerVehicle()
          };

          for (int i = 0; i < values.length; i++) {
            ps.setObject(i + 1, values[i]);
          }

          ps.executeUpdate();
          conn.commit();
        }
      } catch (SQLException e) {
        if (conn != null) {
          conn.rollback();
        }
        throw new RuntimeException("Lỗi database khi thêm khách hàng: " + e.getMessage(), e);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Lỗi kết nối hệ thống!", e);
    }
  }

  @Override
  public void updateCustomerSelective(int id, Map<String, Object> fieldsToUpdate) {
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
    } catch (SQLException e) {
      throw new RuntimeException("Lỗi cập nhật thông tin khách hàng!", e);
    }
  }

  @Override
  public Customer getCustomerById(int id) {
    try (Connection conn = DBConnector.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(CustomerQueries.SELECT_SQL)) {

      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Customer(
              rs.getString("name"),
              rs.getString("phone_number"),
              rs.getString("address"),
              rs.getBigDecimal("account_balance")
          );
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Lỗi khi truy vấn khách hàng id: " + id, e);
    }
    return null;
  }

}
