package com.github.ivankliuk.crudser

import cats.effect.Sync
import cats.implicits._
import com.github.ivankliuk.crudser.api.CreateUser
import com.github.ivankliuk.crudser.errors.instances._
import com.github.ivankliuk.crudser.errors.syntax._
import com.github.ivankliuk.crudser.services.UserService
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

object Routes {

  def userRoutes[F[_] : Sync](userService: UserService[F]): HttpRoutes[F] = {

    val dsl = new Http4sDsl[F] {}

    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "users" / email =>
        userService.get(email).flatMap {
          case Some(user) => Ok(user)
          case None => NotFound(s"Email $email not found".toJson)
        }
      case request@POST -> Root / "users" =>
        request.decode[CreateUser] { user =>
          userService.create(user.email, user.userId)
            .flatMap {
              case Right(()) => Created(s"User ${user.email} created".toJson)
              case Left(error) => BadRequest(s"Failed to create user: $error".toJson)
            }
        }
      case DELETE -> Root / "users" / email =>
        userService.delete(email).flatMap {
          case Right(()) => Ok(s"User $email deleted".toJson)
          case Left(error) => BadRequest(s"Failed to delete user: $error".toJson)
        }
    }
  }

}
