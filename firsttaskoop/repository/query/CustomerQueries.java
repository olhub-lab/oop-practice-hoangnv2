package firsttaskoop.repository.query;

import firsttaskoop.constant.CustomerConstants;

public interface CustomerQueries {

  String INSERT_SQL = "INSERT INTO "
      + CustomerConstants.TABLE_NAME + "("
      + CustomerConstants.COL_NAME + ", "
      + CustomerConstants.COL_PHONE + ", "
      + CustomerConstants.COL_BALANCE + ", "
      + CustomerConstants.COL_LOYALTY + ", "
      + CustomerConstants.COL_ADDRESS + ", "
      + CustomerConstants.COL_VEHICLE +  ") VALUES(?,?,?,?,?,?,?)";

  String SELECT_SQL = "SELECT * FROM" + CustomerConstants.TABLE_NAME + " Where id = ?";
}
