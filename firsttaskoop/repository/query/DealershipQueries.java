package firsttaskoop.repository.query;

import firsttaskoop.constant.DealershipConstants;

public interface DealershipQueries {
  String INSERT_SQL = "INSERT INTO "
      + DealershipConstants.TABLE_NAME + " ("
      + DealershipConstants.COL_NAME + ")"
      + " VALUES (?)";

  String READ_SQL = "SELECT * FROM "
      + DealershipConstants.TABLE_NAME;

  String UPDATE_SQL = "UPDATE " +  DealershipConstants.TABLE_NAME
      + " SET "  + DealershipConstants.COL_NAME + " = ?"
      + " WHERE " + DealershipConstants.COL_DEALERID + " = ?";
}
