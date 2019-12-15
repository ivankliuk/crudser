package com.github.ivankliuk.crudser.repositories

import cats.effect.IO
import com.github.ivankliuk.crudser.db._
import com.github.ivankliuk.crudser.domain.{Email, User}
import doobie.implicits._

trait UserRepository[F[_]] {
  def create(user: User): F[Either[String, Unit]]

  def get(email: Email): F[Option[User]]

  def delete(email: Email): F[Either[String, Unit]]
}

object UserRepository {

  implicit val userRepositoryIO: UserRepository[IO] =
    new UserRepository[IO] {
      def create(user: User): IO[Either[String, Unit]] =
        sql"""
          INSERT INTO "user" (email, id, first_name, last_name)
          VALUES (${user.email}, ${user.id}, ${user.firstName}, ${user.lastName})
        """
          .update
          .run
          .toIO
          .attempt
          .map {
            _.fold(
              t => Left(t.getMessage),
              _ => Right(()))
          }

      def get(email: Email): IO[Option[User]] =
        sql"""
          SELECT email, id, first_name, last_name
          FROM "user"
          WHERE email = $email
        """
          .query[User]
          .option
          .toIO

      def delete(email: Email): IO[Either[String, Unit]] =
        sql"""
          DELETE
          FROM "user"
          WHERE email = $email
        """
          .update
          .run
          .toIO
          .attempt
          .map {
            _.fold(
              t => Left(t.getMessage),
              rowsAffected => if (rowsAffected == 0) Left(s"Email $email not found") else Right(())
            )
          }

    }
}
