package science.service

import cats.Applicative

import java.util.UUID

class IdServiceImpl[F[_]: Applicative] extends IdService[F] {
   def uuid: F[String] = implicitly[Applicative[F]].pure(UUID.randomUUID().toString)
}

trait IdService[F[_]] {
  def uuid: F[String]
}
