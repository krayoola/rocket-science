package science.endpoint

import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

class PurchaseEndpoint[F[_]: Sync] extends Http4sDsl[F] {
  private def purchaseEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case POST -> Root / "purchase" =>
       Ok("purchase route here")
    }

  def endpoint: HttpRoutes[F] = purchaseEndpoint

}

object PurchaseEndpoint {
  def apply[F[_]: Sync] = new PurchaseEndpoint[F]
}
