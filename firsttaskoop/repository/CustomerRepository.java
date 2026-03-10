package firsttaskoop.repository;

import firsttaskoop.model.Customer;
import java.sql.SQLException;
import java.util.Map;

public interface CustomerRepository { ;

  void insert(Customer cus);

  void updateCustomerSelective(int id, Map<String, Object> fieldToUpdate);

  Customer getCustomerById(int id);


}