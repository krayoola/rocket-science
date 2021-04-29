package science.service

import cats.effect.Sync
import science.protocol.Item
import cats.implicits._
import science.dao.ItemDao

/**
 * @param idService id generator
 * @param itemDao item dataa access
 * @tparam F effect type
 */
class ItemStockImpl[F[_]: Sync](idService: IdService[F], itemDao: ItemDao[F]) {

  def insert(item: Item): F[Item]= for {
    uuid <- idService.uuid
    // temporary fix to ease integration.
    // ATM it is considered as business logic :p
    finalItem = item.id.fold(
      item.copy(id = Option(uuid)))(
      _ => item
    )

    _ <- itemDao.insert(uuid, finalItem)
  } yield item

  def select(id: String): F[Option[Item]]= itemDao.select(id)

  def selectAll:F[List[Item]] = itemDao.selectAll

  def update(id: String, item: Item):F[Item] = itemDao.update(id, item)

}

object ItemStockImpl {
  def apply[F[_]: Sync](idService: IdService[F], itemDao: ItemDao[F]) =
    new ItemStockImpl[F](idService, itemDao)
}

trait ItemStockService[F[_]] {
  def insert(item: Item): F[Item]

  def get(id: String): F[Option[Item]]

  def selectAll: F[List[Item]]

  def update(id: String, item: Item): F[Item]
}