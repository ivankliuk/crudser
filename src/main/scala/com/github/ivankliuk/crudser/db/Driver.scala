package com.github.ivankliuk.crudser.db

import cats.effect.{ContextShift, IO}
import com.github.ivankliuk.crudser.Configuration._
import doobie._
import doobie.util.transactor.Transactor.Aux

import scala.concurrent.ExecutionContext

object Driver {

  private implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  def getTransactor: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver,
    url,
    user,
    password
  )

}
