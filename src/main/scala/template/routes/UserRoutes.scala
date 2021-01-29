package template.routes

import akka.Done
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import template.model.{AddUserCommand, DeleteUserCommand, EditUserCommand, Summary, User, UserCommand}
import template.util.Codec
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class UserRoutes(databaseActor: ActorSystem[UserCommand])(implicit system: ActorSystem[_]) extends Codec with FailFastCirceSupport {

  implicit val timeout: Timeout = 3.seconds

  val routes: Route = {
    userAddRoute ~ userEditRoute
  }

  def userAddRoute(): Route = {
    pathPrefix("user" / "add") {
      post {
        entity(as[User]) { entity =>
          val reply: Future[Summary] = databaseActor.ask(AddUserCommand(entity, _))

          onComplete(reply) { summary =>
            complete(summary)
          }

        }
      }
    }
  }

  def userEditRoute(): Route = {
    pathPrefix("user" / "edit") {
      put {
        entity(as[User]) { entity =>
          val reply: Future[Summary] = databaseActor.ask(EditUserCommand(entity, _))

          onComplete(reply) { summary =>
            complete(summary)
          }

        }
      }
    }
  }

  def userDeleteRoute(): Route = {
    pathPrefix("user" / "delete") {
      delete {
        parameter("userId") {(userId) =>

          val reply: Future[Done] = databaseActor.ask(DeleteUserCommand(userId, _))

          onComplete(reply) { summary =>
            complete(summary)
          }

        }
      }
    }
  }

}