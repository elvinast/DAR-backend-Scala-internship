package projectWeek3

import model.{FailedResponse, Cosmetics, CosmeticsCreated, SuccessfulResponse}

case class MakeUpManager() {

  var cosmetics: Map[Int, Cosmetics] = Map()
  var cnt = 0

  def createCosmetic(cosmetic: Cosmetics): CosmeticsCreated = {
    cosmetics = cosmetics + (cnt -> cosmetic)
    val response = CosmeticsCreated(cnt)
    cnt += 1
    response
  }

  def getCosmetic(id: Int): Either[FailedResponse, Cosmetics] = {
    cosmetics.get(id) match {
      case Some(cosmetic) => Right(cosmetic)
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }

  def putCosmetic(id: Int, cosmetic: Cosmetics): Either[FailedResponse, SuccessfulResponse] = {
    cosmetics.get(id) match {
      case Some(value) => {
        cosmetics = cosmetics + (id -> cosmetic)
        Right(SuccessfulResponse(s"${value} updated"))
      }
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }

  def deleteCosmetic(id: Int): Either[FailedResponse, SuccessfulResponse] = {
    cosmetics.get(id) match {
      case Some(cosmetic) => {
        Right(SuccessfulResponse(s"${cosmetic} removed "))
      }
      case None => Left(FailedResponse("No such cosmetic exists :("))
    }
  }







}

