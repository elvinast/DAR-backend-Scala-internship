package template.actor

import akka.Done
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import template.database.Database
import template.model.{AddUserCommand, DeleteUserCommand, EditUserCommand, Summary, UserCommand}

object DatabaseActor {
  def apply(database: Database): Behavior[UserCommand] = {
    Behaviors.receive { (context, message) =>
      message match {
        case cmd: AddUserCommand =>
          println("Add " + cmd)
          cmd.replyTo ! Summary(database.addUser(cmd.user))
          Behaviors.same

        case cmd: EditUserCommand =>
          println("Edit " + cmd)
          cmd.replyTo ! Summary(database.addUser(cmd.user))
          Behaviors.same

        case cmd: DeleteUserCommand =>
          println("Delete " + cmd)
          cmd.replyTo ! {
            if(database.deleteUser(cmd.userId))
              Done
            else
              throw Exception}
          Behaviors.same
      }

    }
  }
}
