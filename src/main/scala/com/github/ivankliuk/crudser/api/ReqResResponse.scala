package com.github.ivankliuk.crudser.api

import cats.effect.Sync
import io.circe.{Decoder, HCursor}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

final case class ReqResResponse(firstName: String, lastName: String)

object ReqResResponse {
  implicit val decoder: Decoder[ReqResResponse] = (c: HCursor) =>
    for {
      firstName <- c.downField("data").get[String]("first_name")
      lastName <- c.downField("data").get[String]("last_name")
    } yield ReqResResponse(firstName, lastName)

  implicit def entityDecoder[F[_]: Sync]: EntityDecoder[F, ReqResResponse] =
    jsonOf

}