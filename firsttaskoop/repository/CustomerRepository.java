package firsttaskoop.repository;

import firsttaskoop.model.Customer;
import java.sql.*;

public interface CustomerRepository {

  void insert(Customer cus) throws SQLException;


}