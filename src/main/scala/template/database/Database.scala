package template.database

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import template.model.User

class Database {

  private val hicaryConf = new HikariConfig()

  private val ds = {
    hicaryConf.setDataSourceClassName(null)
    hicaryConf.setDriverClassName("org.postgresql.Driver")

    hicaryConf.setJdbcUrl("jdbc:postgresql://dev-postgresql-v965.c5uhwnscdeev.eu-west-1.rds.amazonaws.com:5432/dev_bpm")
    hicaryConf.setUsername("dev_bpm")
    hicaryConf.setPassword("AeR8V0eGi1Ha9")

    new HikariDataSource(hicaryConf)
  }


  def getConnection() = {
    ds.getConnection()
  }


  def addUser(user: User): User = {
    val sql = "INSERT INTO public.user_lesson(user_id, email, user_name, last_name, first_name, \"password\")\nVALUES(?, ?, ?, ?, ?, ?)"

    val conn = getConnection()

    val pst = conn.prepareStatement(sql)

    pst.setString(1, user.userId)
    pst.setString(2, user.email)
    pst.setString(3, user.userName)
    pst.setString(4, user.lastName)
    pst.setString(5, user.firstName)
    pst.setString(6, user.password)

    pst.execute()

    conn.close
    user
  }

  def editUser(user: User): User = {
    user
  }

  def deleteUser(userId: String): Boolean = {
    true
  }

  def getUser(userId: String): User = {
    val sql = "SELECT user_id, email, user_name, last_name, first_name, \"password\"\nFROM public.user_lesson where user_id = ?"

    var userId: String = ""
    var email: String = ""
    var userName: String = ""
    var lastName: String = ""
    var firstName: String = ""
    var password: String = ""

    val conn = getConnection()

    val pst = conn.prepareStatement(sql)

    pst.setString(1, userId)

    val rs = pst.executeQuery()

    while (rs.next()) {
      userId = rs.getString("user_id")
      email = rs.getString("email")
      userName = rs.getString("user_name")
      lastName = rs.getString("last_name")
      firstName = rs.getString("first_name")
      password = rs.getString("password")
    }

    User(userId, email, userName, lastName, firstName, password)
  }


  /*
  def getCuSlimeTableID(co: CuSlime) = {
    logger.info("getCuSlimeTableID -> Get table ID for update")
    var listDouble:ListBuffer[Double] = ListBuffer.empty
    var id = 0
    val sql = s"SELECT ID, ValueVMT, ValueSMT, ValueHumid FROM $cuSlime where ReportId = ? and DateValue = ? and Batch = ? "
    try {
      val conn = ds.getConnection()
      try {
        val pst = conn.prepareStatement(sql)
        pst.setString(1, co.reportId)
        co.dateValue match {
          case Some(v) =>  {
            pst.setTimestamp(2, new Timestamp(co.dateValue.get.getMillis))
          }
          case None => pst.setString(2, null)
        }
        pst.setInt(3, co.batch.get)
        val rs = pst.executeQuery()
        while (rs.next()) {
          if(rs.getInt("id") > 0)
            id = rs.getInt("id")
          logger.info("id is {}", rs.getInt("id"))
          listDouble += rs.getDouble("ValueVMT")
          listDouble += rs.getDouble("ValueSMT")
          listDouble += rs.getDouble("ValueHumid")
        }
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        conn.close()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    (id, listDouble.toList)
  }
def setCuOperationsUpdate(id: Int, co: CuOperations): Int = {
    val sql = s"UPDATE $cuOperations " +
      "SET TS = ?, OperationID = ?, Quantity = ?, Weight = ?, BatchString = ?, WShift = ?, Deleted = ?, ReportDate = ? " +
      "WHERE ID = " + id
    val sqlDeleted = s"UPDATE $cuOperations " +
      "SET Deleted = ? WHERE ID = " + id
    val conn = ds.getConnection
    val changedLines = {
      try {
        if (co.deleted == 1) {
          val pst = conn.prepareStatement(sqlDeleted)
          pst.setByte(1, co.deleted)
          pst.executeUpdate()
        } else {
          val pst = conn.prepareStatement(sql)
          pst.setTimestamp(1, new Timestamp(co.ts.getMillis))
          pst.setInt(2, co.operationId.get)
          pst.setDouble(3, co.quantity.orElse(Some(0.toDouble)).get)
          pst.setDouble(4, co.weight.orElse(Some(0.toDouble)).get)
          co.batchString match {
            case Some(value) => pst.setString(5, value)
            case None => pst.setString(5, null)
          }
          pst.setInt(6, co.wShift.get)
          pst.setByte(7, co.deleted)
          pst.setTimestamp(8, new Timestamp(co.reportDate.getMillis))
          pst.executeUpdate()
        }
      } catch {
        case e: Exception =>{
          logger.error("setCuOperationsUpdate -> " + e.getMessage)
          0
        }
      } finally {
        conn.close()
      }
    }
    changedLines
  }
  * */


}