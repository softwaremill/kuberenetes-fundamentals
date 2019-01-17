package com.softwaremill

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor

object Application extends App {

  implicit val system: ActorSystem                        = ActorSystem("kubernetes-fundamentals-system")
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val config = ConfigFactory.load()

  val route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "Hello!"))
      }
    } ~ path("health") {
      get {
        complete(
          HttpResponse(StatusCodes.OK, entity = HttpEntity.Empty)
        )
      }
    }

  val bindingFuture = Http()
    .bindAndHandle(
      route,
      config.getString("http.host"),
      config.getInt("http.port")
    )

}
