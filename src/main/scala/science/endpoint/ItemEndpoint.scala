package science.endpoint

import cats.effect.Sync
import cats.syntax.all._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

class ItemEndpoint[F[_]: Sync] extends Http4sDsl[F] {
  private def getEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "item" =>
       Ok("Item json here")
    }

  private def getAllEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "items" =>
        Ok("Items json here")
    }

  private def updateEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case PUT -> Root / "item" =>
        Ok("Items json here")
    }

  def endpoint: HttpRoutes[F] = getEndpoint <+> getAllEndpoint <+> updateEndpoint

}

object ItemEndpoint {
  def apply[F[_]: Sync] = new ItemEndpoint[F]
}
