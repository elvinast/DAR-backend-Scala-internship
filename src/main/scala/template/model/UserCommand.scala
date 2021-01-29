package template.model

import akka.Done
import akka.actor.typed.ActorRef
import akka.pattern.StatusReply



trait UserCommand

case class AddUserCommand(user: User, replyTo: ActorRef[Summary]) extends UserCommand

case class EditUserCommand(user: User, replyTo: ActorRef[Summary]) extends UserCommand

case class DeleteUserCommand(userId: String, replyTo: ActorRef[Done]) extends UserCommand