package firsttaskoop.repository.impl;

import firsttaskoop.constant.CommonConstants;
import firsttaskoop.constant.DealershipConstants;
import firsttaskoop.exception.DataAccessException;
import firsttaskoop.model.Dealership;
import firsttaskoop.repository.DBConnector;
import firsttaskoop.repository.DBUtils;
import firsttaskoop.repository.DealershipRepository;
import firsttaskoop.repository.query.DealershipQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealershipRepoImpl implements DealershipRepository {

  @Override
  public List<Dealership> findAll() {
    List<Dealership> list = new ArrayList<>();

    try (Connection connection = DBConnector.getInstance().getConnection()) {
      PreparedStatement ps = connection.prepareStatement(DealershipQueries.READ_SQL);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        list.add(mapRowToDealership(rs));
      }

    } catch (SQLException e) {
      throw new DataAccessException("Lỗi không thể tìm thấy dữ liệu dealer: " + e.getMessage());
    }
    return list;
  }

  private Dealership mapRowToDealership(ResultSet rs) throws SQLException {
    String name = rs.getString(DealershipConstants.COL_NAME);

    Dealership dealership = new Dealership(name);
    return dealership;
  }


  @Override
  public void insert(Dealership dealer) {
    try (Connection connection = DBConnector.getInstance().getConnection()) {
      PreparedStatement ps = connection.prepareStatement(DealershipQueries.INSERT_SQL);
      int index = CommonConstants.INDEX;
      ps.setString(++index, dealer.getName());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException("Lỗi thêm đại lý: " +e.getMessage());
    }
  }

  public void update(Dealership dealer) {
    Connection conn = null;
    try {
      conn = DBConnector.getInstance().getConnection();
      conn.setAutoCommit(false);

      updateBaseDealer(conn, dealer);

      conn.commit();
    } catch (SQLException e) {
      DBUtils.rollback(conn);
    }
  }

  private void updateBaseDealer(Connection conn, Dealership dealer) {
    try (PreparedStatement ps = conn.prepareStatement(DealershipQueries.UPDATE_SQL)) {
      int index = CommonConstants.INDEX;

      ps.setString(++index, dealer.getName());
      ps.setInt(++index, dealer.getId());

      ps.executeUpdate();
    }  catch (SQLException e) {
      throw new DataAccessException("Lỗi cập nhập dealer: " + e.getMessage());
    }
  }
}
