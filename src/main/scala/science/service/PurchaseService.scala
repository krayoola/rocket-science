package science.service

import cats.effect.Sync
import cats.implicits._
import science.dao.ItemDao
import science.protocol.{Purchase, PurchaseResponse}

class PurchaseImpl[F[_]: Sync](itemDao: ItemDao[F]) extends PurchaseService[F] {
  override def purchase(purchase: Purchase): F[PurchaseResponse] = {
    for {
      items <- purchase.ids.traverse(itemDao.findAndRemove)
      flattenItems = items.flatten
      totalPrice = flattenItems.map(_.price).sum
    } yield PurchaseResponse(flattenItems, totalPrice)
  }
}

object PurchaseImpl {
  def apply[F[_]: Sync](itemDao: ItemDao[F]) = new PurchaseImpl[F](itemDao)
}

trait PurchaseService[F[_]] {
  def purchase(purchase: Purchase) : F[PurchaseResponse]
}
