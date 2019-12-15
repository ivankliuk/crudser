package com.github.ivankliuk.crudser.services

import cats.effect.Sync
import cats.implicits._
import com.github.ivankliuk.crudser.api.ReqResResponse
import com.github.ivankliuk.crudser.domain.{Email, User, UserId}
import com.github.ivankliuk.crudser.repositories.UserRepository
import org.http4s.Method._
import org.http4s._
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl

trait UserService[F[_]] {
  def create(email: Email, userId: UserId): F[Either[String, Unit]]

  def get(email: Email): F[Option[User]]

  def delete(email: Email): F[Either[String, Unit]]
}

object UserService {

  def apply[F[_] : Sync](client: Client[F])(implicit repo: UserRepository[F]): UserService[F] = {
    new UserService[F] {
      val dsl = new Http4sClientDsl[F] {}

      import dsl._

      def create(email: Email, userId: UserId): F[Either[String, Unit]] = {
        client.expect[ReqResResponse](GET(uri"https://reqres.in/api/users" / userId.toString ))
          .flatMap(r => repo.create(User(email, userId, r.firstName, r.lastName)))
      }

      def get(email: String): F[Option[User]] = repo.get(email)

      def delete(email: Email): F[Either[String, Unit]] = repo.delete(email)

    }
  }

}
