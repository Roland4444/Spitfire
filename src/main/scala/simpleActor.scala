import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class FirstActor extends Actor {
  def receive = {

    case x:String       => {println(x+"     "+self)
                    sender!"fuck off"}
  }
}

class SecondActor extends Actor {
  def receive = {
    case f      => println("Just received"+ f)
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val FirstActor = system.actorOf(Props[FirstActor], name = "firstactor")
  val SecondActor = system.actorOf(Props[SecondActor], name = "secondactor")

  FirstActor ! "hello"

  FirstActor.tell("mx", SecondActor)




}