package firsttaskoop.repository;

import firsttaskoop.model.*;
import java.util.List;

public interface VehicleRepository {

  void insert(Vehicle vehicle);

  List<Vehicle> findAll();

  void update(Vehicle vehicle);



}