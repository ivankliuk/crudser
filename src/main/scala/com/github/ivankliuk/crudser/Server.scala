package com.github.ivankliuk.crudser

import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import com.github.ivankliuk.crudser.Configuration._
import com.github.ivankliuk.crudser.repositories.UserRepository
import com.github.ivankliuk.crudser.services.UserService
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext.global

object Server {

  def stream[F[_] : ConcurrentEffect : UserRepository]
  (implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream

      userAlg = UserService.apply[F](client)
      httpApp = Routes.userRoutes[F](userAlg).orNotFound
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F]
        .bindHttp(serverPort, serverHost)
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
    }.drain
}
