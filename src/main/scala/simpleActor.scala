import java.io.File

import Main.{create, walkTree}
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class FirstActor extends Actor {
  def receive = {

    case f:Any       => {println(f)
                    }
  }
}

class SecondActor extends Actor {
  var i = 0
  def receive = {
    case f      => { i+=1
      print(i)
      println(self + "==>Just received   "+ f)
    }
  }
}

object JustSendHello extends App {
  val system = ActorSystem("spitfire")
  val FirstActor = system.actorOf(Props[FirstActor], name = "firstactor")
  val SecondActor = system.actorOf(Props[SecondActor], name = "secondactor")
  FirstActor.tell("mx", SecondActor)
  val remoteActor = system.actorSelection("akka://hurricane@80.87.98.30:25555/user/firstactor")
  val startTime = System.currentTimeMillis

  System.out.println("total=" + elapsedTime / 1000 + "   Sekunden")
  for (i <- 0 to 40000){
    println(i)
    remoteActor.tell("ПРИВЕТ!!!!", SecondActor)
  }
  val stopTime = System.currentTimeMillis
  val elapsedTime = stopTime - startTime

}


object SendOnceEBS extends App {
  val system = ActorSystem("sendEBS")
  val FirstActor = system.actorOf(Props[FirstActor], name = "firstactor")
  val SecondActor = system.actorOf(Props[SecondActor], name = "secondactor")
  FirstActor.tell("mx", SecondActor)
  val remoteActor = system.actorSelection("akka://CheckSoundService@80.87.98.30:25555/user/checkActor")
  val filename = "/home/roland/Downloads/download/.build_l64/tests_data/S00000000001/fujimi_-10_dB_back_65-dB.wav"


  for(f <- walkTree(new File(filename)) if f.getName.endsWith(".wav")) {
    println("JUST SEND"+f.getAbsolutePath)
    val created = create(f.getPath)
    remoteActor.tell(created, FirstActor)
  }

}