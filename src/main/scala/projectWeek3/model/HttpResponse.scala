package projectWeek3.model

sealed trait HttpResponse
case class SuccessfulResponse(message: String) extends HttpResponse
case class FailedResponse(message: String) extends HttpResponse
case class CosmeticsCreated(id: Int) extends HttpResponse
