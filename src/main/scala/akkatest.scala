import akka.actor.Actor;          // Importing actor trait
import akka.actor.ActorSystem;
import akka.actor.Props;

class AkkaTest extends Actor{    // Extending actor trait
  def receive = {                 //  Receiving message
    case msg:String => println(msg)
    case _ =>println("Unknown message")      // Default case
  }
}

object Main{
  def main(args:Array[String]){
    var actorSystem = ActorSystem("ActorSystem");                       // Creating ActorSystem
    var actor = actorSystem.actorOf(Props[AkkaTest],"HelloAkka")        //Creating actor
    actor ! "Hello Akka"                                                // Sending messages by using !
    actor ! 100.52
  }
}