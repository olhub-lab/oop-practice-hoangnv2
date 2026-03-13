package firsttaskoop.repository;

import firsttaskoop.model.Dealership;
import java.util.List;

public interface DealershipRepository {

  List<Dealership> findAll();

  void insert(Dealership dealer);

  void update(Dealership dealer);

}