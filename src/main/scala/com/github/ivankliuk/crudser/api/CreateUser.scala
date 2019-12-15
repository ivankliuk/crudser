package com.github.ivankliuk.crudser.api

import cats.effect.Sync
import io.circe.{Decoder, HCursor}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

final case class CreateUser(email: String, userId: Int)

object CreateUser {
  implicit val decoder: Decoder[CreateUser] = (c: HCursor) =>
    for {
      email <- c.get[String]("email")
      userId <- c.get[Int]("user_id")
    } yield CreateUser(email, userId)

  implicit def entityDecoder[F[_]: Sync]: EntityDecoder[F, CreateUser] =
    jsonOf

}
