package firsttaskoop.repository.query;

import firsttaskoop.constant.VehicleConstants;

public interface VehicleQueries {
  String INSERT_VEHICLE = "INSERT INTO "
      + VehicleConstants.TABLE_NAME + " ("
      + VehicleConstants.COL_NAME + ","
      + VehicleConstants.COL_MANUFACTURER + ","
      + VehicleConstants.COL_BIRTH + ","
      + VehicleConstants.COL_BASEPRICE + ","
      + VehicleConstants.COL_TAXIMPORT + ","
      + VehicleConstants.COL_QUANTITY + ","
      + VehicleConstants.COL_ORIGIN + ","
      + VehicleConstants.COL_VEHICLETYPE + ","
      + VehicleConstants.COL_DEALERID + ")"
      + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

  String INSERT_CAR = "INSERT INTO "
      + VehicleConstants.TABLE_CAR + " ("
      + VehicleConstants.COL_VEHICLEID + ","
      + VehicleConstants.COL_SEATNUMBER + ","
      + VehicleConstants.COL_CARCAPACITY + ","
      + VehicleConstants.COL_BODYTYPE + ","
      + VehicleConstants.COL_FUELTYPE + ")"
      + " VALUES (?, ?, ?, ?, ?)";

  String INSERT_MOTORBIKE = "INSERT INTO "
      + VehicleConstants.TABLE_MOTORBIKE + " ("
      + VehicleConstants.COL_VEHICLEID + ","
      + VehicleConstants.COL_MOTORCAPACITY + ","
      + VehicleConstants.COL_POWER + ","
      + VehicleConstants.COL_TYPEOFMOTORBIKE + ")"
      + " VALUES (?, ?, ?, ?)";

  String INSERT_BIKE = "INSERT INTO "
      + VehicleConstants.TABLE_BIKE + " ("
      + VehicleConstants.COL_VEHICLEID + ","
      + VehicleConstants.COL_BIKETYPE + ","
      + VehicleConstants.COL_FRAMEMATERIAL + ")"
      + " VALUES (?, ?, ?)";

  String FIND_ALL = "SELECT v.*, "
      + "c." + VehicleConstants.COL_SEATNUMBER + ", "
      + "c." + VehicleConstants.COL_CARCAPACITY + " AS car_capacity, "
      + "c." + VehicleConstants.COL_BODYTYPE + ", "
      + "c." + VehicleConstants.COL_FUELTYPE + ", "
      + "m." + VehicleConstants.COL_MOTORCAPACITY + " AS motor_capacity, "
      + "m." + VehicleConstants.COL_POWER + ", "
      + "m." + VehicleConstants.COL_TYPEOFMOTORBIKE + ", "
      + "b." + VehicleConstants.COL_BIKETYPE + ", "
      + "b." + VehicleConstants.COL_FRAMEMATERIAL
      + " FROM " + VehicleConstants.TABLE_NAME + " v "
      + "LEFT JOIN " + VehicleConstants.TABLE_CAR + " c ON v.id = c." + VehicleConstants.COL_VEHICLEID + " "
      + "LEFT JOIN " + VehicleConstants.TABLE_MOTORBIKE + " m ON v.id = m." + VehicleConstants.COL_VEHICLEID + " "
      + "LEFT JOIN " + VehicleConstants.TABLE_BIKE + " b ON v.id = b." + VehicleConstants.COL_VEHICLEID;

  String UPDATE_VEHICLE = "UPDATE " + VehicleConstants.TABLE_NAME + " SET "
      + VehicleConstants.COL_NAME + " = ?, "
      + VehicleConstants.COL_MANUFACTURER + " = ?, "
      + VehicleConstants.COL_BIRTH + " = ?, "
      + VehicleConstants.COL_BASEPRICE + " = ?, "
      + VehicleConstants.COL_TAXIMPORT + " = ?, "
      + VehicleConstants.COL_QUANTITY + " = ?, "
      + VehicleConstants.COL_ORIGIN + " = ? "
      + " WHERE id = ?";

  String UPDATE_CAR = "UPDATE " + VehicleConstants.TABLE_CAR + " SET "
      + VehicleConstants.COL_SEATNUMBER + " = ?, "
      + VehicleConstants.COL_CARCAPACITY + " = ?, "
      + VehicleConstants.COL_BODYTYPE + " = ?, "
      + VehicleConstants.COL_FUELTYPE + " = ? "
      + " WHERE " + VehicleConstants.COL_VEHICLEID + " = ?";

  String UPDATE_MOTORBIKE = "UPDATE " + VehicleConstants.TABLE_MOTORBIKE + " SET "
      + VehicleConstants.COL_MOTORCAPACITY + " = ?, "
      + VehicleConstants.COL_POWER + " = ?, "
      + VehicleConstants.COL_TYPEOFMOTORBIKE + " = ? "
      + " WHERE " + VehicleConstants.COL_VEHICLEID + " = ?";

  String UPDATE_BIKE = "UPDATE " + VehicleConstants.TABLE_BIKE + " SET "
      + VehicleConstants.COL_BIKETYPE + " = ?, "
      + VehicleConstants.COL_FRAMEMATERIAL + " = ? "
      + " WHERE " + VehicleConstants.COL_VEHICLEID + " = ?";
}
