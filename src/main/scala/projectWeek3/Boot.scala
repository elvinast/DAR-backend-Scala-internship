package projectWeek3

import projectWeek3.model.{FailedResponse, Cosmetics}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContextExecutor

object Boot extends App with JsonSerializer {

  implicit val system: ActorSystem = ActorSystem("http-server")
  implicit val materializer: ActorMaterializer = ActorMaterializer() //materialise stream
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def readInt(str: String): Option[Int] = {
    if (str matches "\\d+") Some(str.toInt) else None
  }

  val makeUpManager = MakeUpManager()

  val route: Route =
    concat(
      path("check") {
        get {
          complete {
            "Ok"
          }
        }
      },
      pathPrefix("cosmetics") {
        concat(
          post {
            entity(as[Cosmetics]) { cosmetic =>
              complete{
                makeUpManager.createCosmetic(cosmetic)
              }
            }
          },
          path(Segment) { cosmeticID =>
            get {
              complete{
                readInt(cosmeticID) match {
                  case Some(id) => makeUpManager.getCosmetic(id)
                  case None => FailedResponse("Wrong id format")
                }
              }
            }
          },
          path(Segment) { cosmeticID =>
            put {
              entity(as[Cosmetics]) { cosmetic =>
                complete{
                  readInt(cosmeticID) match {
                    case Some(id) => makeUpManager.putCosmetic(id, cosmetic)
                    case None => FailedResponse("Wrong id format")
                  }
                }
              }
            }
          },
          path(Segment) { cosmeticID =>
            delete {
              complete {
                readInt(cosmeticID) match {
                  case Some(id) => makeUpManager.deleteCosmetic(id)
                  case None => FailedResponse("Wrong id format")
                }
              }
            }
          }
        )
      }
    )

  Http().bindAndHandle(route, "0.0.0.0", 8080)
}
