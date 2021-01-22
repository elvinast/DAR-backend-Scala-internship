package projectWeek3

import model.{FailedResponse, Cosmetics, CosmeticsCreated, SuccessfulResponse}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSerializer extends DefaultJsonProtocol{
  implicit val cosmeticCreatedFormat: RootJsonFormat[CosmeticsCreated] = jsonFormat1(CosmeticsCreated)
  implicit val cosmeticFormat: RootJsonFormat[Cosmetics] = jsonFormat3(Cosmetics)
  implicit val failedResponse: RootJsonFormat[FailedResponse] = jsonFormat1(FailedResponse)
  implicit val successfulResponse: RootJsonFormat[SuccessfulResponse] =  jsonFormat1(SuccessfulResponse)
}