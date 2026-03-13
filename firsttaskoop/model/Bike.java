package firsttaskoop.model;

import firsttaskoop.enums.VehicleType;
import java.math.BigDecimal;
import firsttaskoop.enums.Origin;

public class Bike extends Vehicle {

  private static final BigDecimal BIKE_IMPORT_TAX = new BigDecimal("0.1");
  private String bikeType;
  private String frameMaterial;

  public Bike(String nameModel, String manufacturer, int birthYear, BigDecimal originalPrice,
      Origin origin, BigDecimal importTaxRate, int quantity,
      String bikeType, String frameMaterial) {

    super(nameModel, manufacturer, birthYear, originalPrice, origin, importTaxRate, quantity);

    this.bikeType = bikeType;
    this.frameMaterial = frameMaterial;
  }

  public String getBikeType() {
    return bikeType;
  }

  public void setBikeType(String bikeType) {
    this.bikeType = bikeType;
  }

  public String getFrameMaterial() {
    return frameMaterial;
  }

  public void setFrameMaterial(String frameMaterial) {
    this.frameMaterial = frameMaterial;
  }

  @Override
  public BigDecimal getExciseTax() {

    return BigDecimal.ZERO;
  }

  @Override
  public VehicleType getVehicleType() {
    return VehicleType.BIKE;
  }

}