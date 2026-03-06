package firsttaskoop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import firsttaskoop.enums.LoyaltyLevel;

public class Customer {

  private static final int MIN_VEHICLE_SILVER = 3;
  private static final int MAX_VEHICLE_SILVER = 5;
  private static final int MIN_VEHICLE_GOLD = 6;
  private static final int MAX_VEHICLE_GOLD = 10;
  private static final int MIN_VEHICLE_PLATINUM = 11;
  private int id;
  private String name;
  private String phoneNumber;
  private String address;
  private BigDecimal accountBalance;
  private LoyaltyLevel level;
  private int ownerVehicle;

  public Customer(int id, String name, String phoneNumber, String address) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.accountBalance = BigDecimal.ZERO;
    this.level = LoyaltyLevel.REGULAR;
    this.ownerVehicle = 0;
  }

  public Customer(String name, String phoneNumber, String address,
      BigDecimal accountBalance) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.accountBalance = accountBalance;
    this.level = updateLoyaltyLevel();
    this.ownerVehicle = 0;
  }

  public int getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public String getName() {

    return name;
  }

  public String getPhoneNumber() {

    return phoneNumber;
  }

  public int getOwnerVehicle() {
    return ownerVehicle;
  }

  public void setOwnerVehicle(int ownerVehicle) {
    this.ownerVehicle = ownerVehicle;
  }

  public BigDecimal getAccountBalance() {

    return accountBalance;
  }

  public void pay(BigDecimal amount) {
    if (checkingBalance(amount)) {
      this.accountBalance = this.accountBalance.subtract(amount);
    }
  }


  public boolean checkingBalance(BigDecimal amount) {

    return accountBalance.compareTo(amount) >= 0;
  }

  public BigDecimal getDiscount() {

    return level.getDiscountRate();
  }

  public LoyaltyLevel getLoyaltyLevel() {
    return level;
  }

  public void setLoyaltyLevel(LoyaltyLevel level) {
    this.level = level;
  }


  public LoyaltyLevel updateLoyaltyLevel() {
    if (ownerVehicle < MIN_VEHICLE_SILVER) {
      return LoyaltyLevel.REGULAR;
    }
    if (ownerVehicle < MAX_VEHICLE_SILVER &&  ownerVehicle >= MIN_VEHICLE_SILVER) {
      return LoyaltyLevel.SILVER;
    }
    if (ownerVehicle < MAX_VEHICLE_GOLD && ownerVehicle >= MIN_VEHICLE_GOLD) {
      return LoyaltyLevel.GOLD;
    }
    return  LoyaltyLevel.PLATINUM;
  }

}