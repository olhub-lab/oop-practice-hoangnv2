package firsttaskoop.model;

import firsttaskoop.enums.VehicleType;
import java.math.BigDecimal;
import firsttaskoop.enums.Origin;

public class MotorBike extends Vehicle {

  private static final BigDecimal MOTORBIKE_IMPORT_TAX = new BigDecimal("0.3");
  private static final int MOTORBIKE_CAPACITY = 150;
  private static final BigDecimal HIGH_MOTORBIKE_CAPACITY_TAX = new BigDecimal("0.2");
  private int motorCapacity;
  private String typeOfMotorBike;
  private int power;

  public MotorBike(String nameModel, String manufacturer, int birthYear, BigDecimal originalPrice,
      Origin origin, BigDecimal importTaxRate, int quantity,
      int motorCapacity, int power, String typeOfMotorBike) {

    super(nameModel, manufacturer, birthYear, originalPrice, origin, importTaxRate, quantity);

    this.motorCapacity = motorCapacity;
    this.power = power;
    this.typeOfMotorBike = typeOfMotorBike;
  }

  public int getCapacity() {
    return motorCapacity;
  }

  public void setCapacity(int motorCapacity) {
    this.motorCapacity = motorCapacity;
  }

  public String getTypeOfMotorBike() {
    return typeOfMotorBike;
  }

  public void setTypeOfMotorBike(String typeOfMotorBike) {
    this.typeOfMotorBike = typeOfMotorBike;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  @Override
  public BigDecimal getExciseTax() {
    BigDecimal basePrice = originalPrice.add(super.getImportTax());
    if (motorCapacity < MOTORBIKE_CAPACITY) {

      return BigDecimal.ZERO;
    }

    return basePrice.multiply(HIGH_MOTORBIKE_CAPACITY_TAX);
  }

  @Override
  public VehicleType getVehicleType() {
    return VehicleType.MOTORBIKE;
  }
}