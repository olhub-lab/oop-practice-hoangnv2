package firsttaskoop.repository.impl;

import firsttaskoop.constant.VehicleConstants;
import firsttaskoop.enums.Origin;
import firsttaskoop.exception.DataAccessException;
import firsttaskoop.model.Bike;
import firsttaskoop.model.Car;
import firsttaskoop.model.MotorBike;
import firsttaskoop.model.Vehicle;
import firsttaskoop.repository.DBConnector;
import firsttaskoop.repository.DBUtils;
import firsttaskoop.repository.VehicleRepository;
import firsttaskoop.repository.query.VehicleQueries;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepoImpl implements VehicleRepository {

  @Override
  public void insert(Vehicle vehicle) {
    try (Connection conn = DBConnector.getInstance().getConnection()) {
      try {
        conn.setAutoCommit(false);

        int generateId = insertBaseVehicle(conn, vehicle);

        insertSubtypeData(conn, generateId, vehicle);

        conn.commit();
        System.out.println("Thêm xe thành công !!!");

      } catch (SQLException e) {
        DBUtils.rollback(conn);
      }
    } catch (SQLException e) {
      throw new DataAccessException("Lỗi khi thêm xe vào cơ sở dữ liệu: " + e.getMessage(), e);
    }
  }


  private int insertBaseVehicle(Connection connection, Vehicle vehicle) {
    try (PreparedStatement ps = connection.prepareStatement(VehicleQueries.INSERT_VEHICLE,
        Statement.RETURN_GENERATED_KEYS)) {
      int i = 1;

      ps.setString(i++, vehicle.getNameModel());
      ps.setString(i++, vehicle.getManufacturer());
      ps.setInt(i++, vehicle.getBirthYear());
      ps.setBigDecimal(i++, vehicle.getBasePrice());
      ps.setBigDecimal(i++, vehicle.getImportTax());
      ps.setInt(i++, vehicle.getQuantity());
      ps.setString(i++, vehicle.getOrigin().name());
      ps.setString(i++, vehicle.getVehicleType().name());

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
      throw new DataAccessException("Insert thành công nhưng không lấy được ID xe.");
    } catch (SQLException e) {
      throw new DataAccessException("Lỗi Database khi chèn thông tin xe cơ bản: " + e.getMessage(),
          e);
    }

  }

  private void insertSubtypeData(Connection conn, int id, Vehicle v) throws SQLException {
    if (v instanceof Car) {
      insertCar(conn, id, (Car) v);
    } else if (v instanceof MotorBike) {
      insertMotorBike(conn, id, (MotorBike) v);
    } else if (v instanceof Bike) {
      insertBike(conn, id, (Bike) v);
    }
  }

  private void insertBike(Connection conn, int id, Bike v) {

    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.INSERT_BIKE)) {
      int i = 1;

      ps.setInt(i++, id);
      ps.setString(i++, v.getBikeType());
      ps.setString(i++, v.getFrameMaterial());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(
          "Lỗi khi thêm thông tin Bike (ID: " + id + "): " + e.getMessage(), e);
    }
  }

  private void insertMotorBike(Connection conn, int id, MotorBike v) {

    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.INSERT_MOTORBIKE)) {
      int i = 1;

      ps.setInt(i++, id);
      ps.setInt(i++, v.getCapacity());
      ps.setInt(i++, v.getPower());
      ps.setString(i++, v.getTypeOfMotorBike());
    } catch (SQLException e) {
      throw new DataAccessException(
          "Lỗi khi thêm thông tin Car (ID: " + id + "): " + e.getMessage(), e);
    }
  }

  private void insertCar(Connection conn, int id, Car v) {
    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.INSERT_CAR)) {
      int i = 1;

      ps.setInt(i++, id);
      ps.setInt(i++, v.getSeatNumber());
      ps.setInt(i++, v.getCapacity());
      ps.setString(i++, v.getBodyType());
      ps.setString(i++, v.getFuelType());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException(
          "Lỗi khi thêm thông tin Car (ID: " + id + "): " + e.getMessage(), e);
    }

  }

  @Override
  public List<Vehicle> findAll() {
    List<Vehicle> list = new ArrayList<>();

    try (Connection conn = DBConnector.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(VehicleQueries.FIND_ALL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        list.add(mapRowToVehicle(rs));
      }

    } catch (SQLException e) {
      throw new DataAccessException("Lỗi khi lấy danh sách xe: " + e.getMessage(), e);
    }
    return list;
  }

  private Vehicle mapRowToVehicle(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String nameModel = rs.getString(VehicleConstants.COL_NAME);
    String manufacturer = rs.getString(VehicleConstants.COL_MANUFACTURER);
    int birthYear = rs.getInt(VehicleConstants.COL_BIRTH);
    BigDecimal originalPrice = rs.getBigDecimal(VehicleConstants.COL_BASEPRICE);
    BigDecimal importTaxRate = rs.getBigDecimal(VehicleConstants.COL_TAXIMPORT);
    int quantity = rs.getInt(VehicleConstants.COL_QUANTITY);

    Origin origin = null;
    String originStr = rs.getString(VehicleConstants.COL_ORIGIN);
    if (originStr != null) {
      origin = Origin.valueOf(originStr.toUpperCase());
    }

    String typeStr = rs.getString(VehicleConstants.COL_VEHICLETYPE);
    Vehicle vehicle = null;

    if ("CAR".equalsIgnoreCase(typeStr)) {
      int seatNumber = rs.getInt(VehicleConstants.COL_SEATNUMBER);
      int carCapacity = rs.getInt(VehicleConstants.COL_CARCAPACITY);
      String bodyType = rs.getString(VehicleConstants.COL_BODYTYPE);
      String fuelType = rs.getString(VehicleConstants.COL_FUELTYPE);

      vehicle = new Car(nameModel, manufacturer, birthYear, originalPrice,
          origin,importTaxRate, quantity,
          seatNumber, fuelType, carCapacity, bodyType);

    } else if ("MOTORBIKE".equalsIgnoreCase(typeStr)) {
      int motorCapacity = rs.getInt("motor_capacity");
      int power = rs.getInt(VehicleConstants.COL_POWER);
      String typeOfMotorbike = rs.getString(VehicleConstants.COL_TYPEOFMOTORBIKE);

      vehicle = new MotorBike(nameModel, manufacturer, birthYear, originalPrice,
          origin, importTaxRate, quantity,
          motorCapacity, power, typeOfMotorbike);

    } else if ("BIKE".equalsIgnoreCase(typeStr)) {
      String bikeType = rs.getString(VehicleConstants.COL_BIKETYPE);
      String frameMaterial = rs.getString(VehicleConstants.COL_FRAMEMATERIAL);

      vehicle = new Bike(nameModel, manufacturer, birthYear, originalPrice,
          origin, importTaxRate, quantity,
          bikeType, frameMaterial);
    }

    if (vehicle != null) {
      vehicle.setId(id);
    }

    return vehicle;
  }

  @Override
  public void update(Vehicle vehicle) {
    Connection conn = null;
    try {
      conn = DBConnector.getInstance().getConnection();
      conn.setAutoCommit(false);

      updateBaseVehicle(conn, vehicle);

      updateSubtypeData(conn, vehicle);

      conn.commit();
      System.out.println("Cập nhật xe ID " + vehicle.getId() + " thành công!");
    } catch (SQLException e) {
      if (conn != null) {
        try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
      }
      throw new DataAccessException("Lỗi khi cập nhật xe: " + e.getMessage(), e);
    }
  }

  private void updateBaseVehicle(Connection conn, Vehicle v) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.UPDATE_VEHICLE)) {
      int i = 1;
      ps.setString(i++, v.getNameModel());
      ps.setString(i++, v.getManufacturer());
      ps.setInt(i++, v.getBirthYear());
      ps.setBigDecimal(i++, v.getOriginalPrice());
      ps.setBigDecimal(i++, v.getImportTax());
      ps.setInt(i++, v.getQuantity());
      ps.setString(i++, v.getOrigin().name());
      ps.setInt(i++, v.getId());

      ps.executeUpdate();
    }
  }

  private void updateSubtypeData(Connection conn, Vehicle v) throws SQLException {
    if (v instanceof Car) {
      updateCar(conn, (Car) v);
    } else if (v instanceof MotorBike) {
      updateMotorBike(conn, (MotorBike) v);
    } else if (v instanceof Bike) {
      updateBike(conn, (Bike) v);
    }
  }

  private void updateBike(Connection conn, Bike bike) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.UPDATE_BIKE)) {
      int i = 1;
      ps.setString(i++, bike.getBikeType());
      ps.setString(i++, bike.getFrameMaterial());

      ps.setInt(i++, bike.getId());

      ps.executeUpdate();
    }
  }

  private void updateMotorBike(Connection conn, MotorBike mb) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.UPDATE_MOTORBIKE)) {
      int i = 1;
      ps.setInt(i++, mb.getCapacity());
      ps.setInt(i++, mb.getPower());
      ps.setString(i++, mb.getTypeOfMotorBike());

      ps.setInt(i++, mb.getId());

      ps.executeUpdate();
    }
  }

  private void updateCar(Connection conn, Car car) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(VehicleQueries.UPDATE_CAR)) {
      int i = 1;
      ps.setInt(i++, car.getSeatNumber());
      ps.setInt(i++, car.getCapacity());
      ps.setString(i++, car.getBodyType());
      ps.setString(i++, car.getFuelType());
      ps.setInt(i++, car.getId());

      ps.executeUpdate();
    }
  }
}
