package projectWeek3

import model.{FailedResponse, Cosmetics, CosmeticsCreated, SuccessfulResponse}
import java.sql.{Connection, DriverManager, ResultSet}

case class MakeUpManager() {

//  println("Postgres connector")
//
//  classOf[org.postgresql.Driver]
//  val con_st = "jdbc:postgresql://localhost:5432/akkahttp?user=elvina"
//  val conn = DriverManager.getConnection(con_st)
//  try {
//    val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
//
//
//    val rs = stm.executeQuery("SELECT * from cosmetics")
//
//    while(rs.next) {
//      println(rs.getString("name"))
//    }
//  } finally {
//    conn.close()
//  }



  var cosmetics: Map[Int, Cosmetics] = Map()
  var cnt = 0

  def createCosmetic(cosmetic: Cosmetics): CosmeticsCreated = { //post
    cosmetics = cosmetics + (cnt -> cosmetic)
    val response = CosmeticsCreated(cnt) //cnt = id
    cnt += 1
    response
  }

  def getCosmetic(id: Int): Either[FailedResponse, Cosmetics] = { //read
    cosmetics.get(id) match {
      case Some(cosmetic) => Right(cosmetic)
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }

  def putCosmetic(id: Int, cosmetic: Cosmetics): Either[FailedResponse, SuccessfulResponse] = { //update
    cosmetics.get(id) match {
      case Some(value) => {
        cosmetics = cosmetics + (id -> cosmetic)
        Right(SuccessfulResponse(s"${value} updated"))
      }
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }

  def deleteCosmetic(id: Int): Either[FailedResponse, SuccessfulResponse] = { // ?
    cosmetics.get(id) match {
      case Some(cosmetic) => {
        Right(SuccessfulResponse(s"${cosmetic} removed "))
      }
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }







}