package science.dao
import cats.Applicative
import science.protocol.Item
import cats.implicits._
import scala.collection.mutable.Map
class ItemDaoImpl[F[_]: Applicative] extends ItemDao[F] {

  // I'm okay for now
  type MyFancyTable = Map[String, Item]
  private val items: MyFancyTable = Map.empty

  override def insert(id: String, item: Item): F[Item] =
    implicitly[Applicative[F]].pure({
      items.put(id, item)
      item
    })

  override def select(id: String): F[Option[Item]] = implicitly[Applicative[F]].pure(
    items.get(id)
  )

  override def selectAll: F[List[Item]] = implicitly[Applicative[F]].pure(
    items.values.toList
  )

  override def update(id: String, item: Item): F[Item] = implicitly[Applicative[F]].pure({
    items.update(id, item)
    item
  })

  override def findAndRemove(id: String): F[Option[Item]] = for {
      result <- select(id)
      _ =   items.remove(id)
    } yield result
}

trait ItemDao[F[_]] {
  def insert(id: String, item: Item) : F[Item]
  def update(id: String, item: Item): F[Item]
  def select(id: String) : F[Option[Item]]
  def findAndRemove(id: String) : F[Option[Item]]
  def selectAll : F[List[Item]]
}