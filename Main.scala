package akka_playground

import akka.actor.{ActorSystem, Address}
import akka.cluster.Cluster


object Main extends App {
  System.setProperty("akka.actor.provider", "akka.cluster.ClusterActorRefProvider")
  System.setProperty("akka.remote.netty.tcp.hostname", "localhost")

  implicit val system = ActorSystem("a")
  val cluster = Cluster(system)
  cluster.joinSeedNodes (Vector (
    new Address("akka.tcp", system.name, "localhost", 2552),
    new Address("akka.tcp", system.name, "localhost", 2553)
  ))

//  Runtime.getRuntime.addShutdownHook(new Thread() {
//    override def run(): Unit = {
//      println ("leaving")
//      cluster.leave(cluster.selfAddress)
//      println ("left")
//      while (!cluster.isTerminated) Thread.sleep(100)
//      println ("terminated")
//    }
//  })
}
