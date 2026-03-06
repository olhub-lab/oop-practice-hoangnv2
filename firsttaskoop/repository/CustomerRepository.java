package firsttaskoop.repository;

import firsttaskoop.model.Customer;
import java.sql.SQLException;
import java.util.Map;

public interface CustomerRepository { ;

  void insert(Customer cus) throws SQLException;

  void updateCustomerSelective(int id, Map<String, Object> fieldToUpdate) throws SQLException;

  Customer getCustomerById(int id) throws SQLException;


}