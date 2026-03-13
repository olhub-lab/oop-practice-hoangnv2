package firsttaskoop.model;

import firsttaskoop.enums.Origin;
import firsttaskoop.enums.VehicleType;
import java.math.BigDecimal;

public class Car extends Vehicle {

  private int seatNumber;
  private String fuelType;
  private int carCapacity;
  private String bodyType;

  public Car(String name, String manu, int year, BigDecimal price, Origin origin,
      BigDecimal importTaxRate, int qty, int seats, String fuel, int cap, String body) {

    super(name, manu, year, price, origin, importTaxRate, qty);

    this.seatNumber = seats;
    this.fuelType = fuel;
    this.carCapacity = cap;
    this.bodyType = body;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public String getFuelType() {
    return fuelType;
  }

  public void setFuelType(String fuelType) {
    this.fuelType = fuelType;
  }

  public int getCapacity() {
    return carCapacity;
  }

  public void setCapacity(int carCapacity) {
    this.carCapacity = Car.this.carCapacity;
  }

  public String getBodyType() {
    return bodyType;
  }

  public void setBodyType(String bodyType) {
    this.bodyType = bodyType;
  }

  @Override
  public BigDecimal getExciseTax() {
    BigDecimal base = originalPrice.add(getImportTax());
    return carCapacity < 3000 ? base.multiply(new BigDecimal("0.5")) : base.multiply(BigDecimal.ONE);
  }

  @Override
  public VehicleType getVehicleType() {
    return VehicleType.CAR;
  }
}