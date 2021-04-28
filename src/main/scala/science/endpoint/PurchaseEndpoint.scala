package science.endpoint

import cats.effect.Sync
import cats.syntax.all._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, HttpRoutes}
import science.dao.ItemDao
import science.protocol.Purchase
import science.service.PurchaseImpl

class PurchaseEndpoint[F[_]: Sync](itemDao: ItemDao[F]) extends Http4sDsl[F] {

  implicit val itemDecoder: EntityDecoder[F, Purchase] = jsonOf

  private def summonPurchase: PurchaseImpl[F] = {
    PurchaseImpl.apply[F](itemDao)
  }

  private def purchaseEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case req @ POST -> Root / "purchase" =>
        for {
          purchase <- req.as[Purchase]
          purchasedItems <- summonPurchase.purchase(purchase)
          result <- Ok(purchasedItems.asJson)
        } yield result
    }

  def endpoint: HttpRoutes[F] = purchaseEndpoint

}

object PurchaseEndpoint {
  def apply[F[_]: Sync](itemDao: ItemDao[F]) = new PurchaseEndpoint[F](itemDao)
}
