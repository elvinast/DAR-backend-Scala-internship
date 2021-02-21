package projectWeek3

import java.sql.{Connection, DriverManager, ResultSet}

object DB extends App {

  println("Postgres connector")

  classOf[org.postgresql.Driver]
  val con_st = "jdbc:postgresql://localhost:5432/akkahttp?user=elvina"
  val conn = DriverManager.getConnection(con_st)
  try {
    val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

    val rs = stm.executeQuery("SELECT * from cosmetics")

    while(rs.next) {
      println(rs.getString("name"))
    }
  } finally {
    conn.close()
  }
}
