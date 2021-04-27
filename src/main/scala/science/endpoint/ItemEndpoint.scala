package science.endpoint

import cats.effect.Sync
import cats.syntax.all._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, HttpRoutes}
import science.dao.ItemDao
import science.protocol.Item
import science.service.{IdService, ItemStockImpl}

class ItemEndpoint[F[_]: Sync](idService: IdService[F], itemDao: ItemDao[F]) extends Http4sDsl[F]  {
  // TODO: Authentication will apply on this layer later.
  // TODO: extract this decoder later in a separate layer
  implicit val itemDecoder: EntityDecoder[F, Item] = jsonOf

  private def summonItemStock: ItemStockImpl[F] = {
    // come on.. you are better than this
    // TODO: Fix me it can be done better by extraction </3 but im working
    ItemStockImpl.apply[F](idService, itemDao)
  }
  private def getEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "item" / id =>
        summonItemStock.select(id).flatMap( v => Ok(v.asJson.dropNullValues))
    }

  private def getAllEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "items" =>
        summonItemStock.selectAll.flatMap( v => Ok(v.asJson.dropNullValues))
    }

  private def updateEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case req @ PUT -> Root / "item" / id => for {
      item <- req.as[Item]
      _ <- summonItemStock.update(id, item)
      result <- Ok(item.asJson)
      } yield result
    }

  private def createEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case req @ POST -> Root / "item" => for {
        item <-  req.as[Item]
        _ <- summonItemStock.insert(item)
        result <- Ok(item.asJson.dropNullValues)
      } yield result
    }

  def endpoint: HttpRoutes[F] = getEndpoint <+>
    getAllEndpoint <+>
    updateEndpoint <+>
    createEndpoint

}

object ItemEndpoint {
  def apply[F[_]: Sync](idService: IdService[F], itemDao: ItemDao[F]) =
    new ItemEndpoint[F](idService, itemDao)
}
