package template.routes

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Directives.{complete, concat, get, pathEndOrSingleSlash, pathPrefix}
import akka.http.scaladsl.server.Route
import com.typesafe.config.Config
import template.model.UserCommand


class HttpRoutes(databaseActor: ActorSystem[UserCommand])(implicit system: ActorSystem[_], config: Config)  {

  val routes: Route = pathPrefix("api") {
    pathPrefix("v1") {
      concat(
        pathEndOrSingleSlash {
          get {
            complete("Health check! Hello!")
          }
        },
        new UserRoutes(databaseActor).routes
      )
    }
  }

}
