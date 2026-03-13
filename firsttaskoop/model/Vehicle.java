package firsttaskoop.model;

import firsttaskoop.enums.Origin;
import firsttaskoop.enums.VehicleType;
import java.math.BigDecimal;

public abstract class Vehicle {

  protected int id;
  protected String nameModel;
  protected String manufacturer;
  protected int birthYear;
  protected BigDecimal originalPrice;
  protected Origin origin;
  protected BigDecimal importTaxRate;
  protected int quantity;

  public Vehicle(String nameModel, String manufacturer, int birthYear, BigDecimal originalPrice,
      Origin origin, BigDecimal importTaxRate, int quantity) {
    this.nameModel = nameModel;
    this.manufacturer = manufacturer;
    this.birthYear = birthYear;
    this.originalPrice = originalPrice;
    this.origin = origin;
    this.importTaxRate = importTaxRate;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNameModel() {
    return nameModel;
  }

  public void setNameModel(String nameModel) {
    this.nameModel = nameModel;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public int getBirthYear() {
    return birthYear;
  }

  public BigDecimal getBasePrice() {
    return originalPrice;
  }

  public void setBirthYear(int birthYear) {
    this.birthYear = birthYear;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(BigDecimal originalPrice) {
    this.originalPrice = originalPrice;
  }

  public Origin getOrigin() {
    return origin;
  }

  public void setOrigin(Origin origin) {
    this.origin = origin;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public abstract BigDecimal getExciseTax();

  public abstract VehicleType getVehicleType();

  public BigDecimal getImportTax() {
    return origin == Origin.IMPORTED ? originalPrice.multiply(importTaxRate) : BigDecimal.ZERO;
  }

  public BigDecimal calculatePrice() {
    return originalPrice.add(getImportTax()).add(getExciseTax());
  }
}