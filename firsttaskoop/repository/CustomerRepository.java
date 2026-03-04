package firsttaskoop.repository;

import firsttaskoop.model.Customer;
import java.sql.*;
import java.util.List;

public interface CustomerRepository {

  void insert(Connection conn, Customer cus) throws SQLException;
}