package science

import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp, Timer}
import cats.implicits._
import science.endpoint.{ItemEndpoint, PurchaseEndpoint}
import fs2.Stream
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext.global

object Server extends IOApp {

  private def server[F[_]: ConcurrentEffect](implicit T: Timer[F]): Stream[F, Nothing] = {
    val itemEndpoint     = ItemEndpoint[F]
    val purchaseEndpoint = PurchaseEndpoint[F]

    val routes = (
      itemEndpoint.endpoint <+>
        purchaseEndpoint.endpoint
      ).orNotFound

    val finalHttpApp = Logger.httpApp(true, true)(routes)

    // TODO: Configurable ports and host
    for {
      exitCode <- BlazeServerBuilder[F](global)
        .bindHttp(8080, "localhost")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain

  def run(args: List[String]): IO[ExitCode] = {
    server[IO].compile.drain.as(ExitCode.Success)
  }
}
