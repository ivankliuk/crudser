package com.github.ivankliuk.crudser

import cats.effect.IO
import doobie.ConnectionIO

package object db {

  implicit class ConnectionIOOps[A](conn: ConnectionIO[A]) {
    def toIO: IO[A] = Driver.getTransactor.trans.apply(conn)
  }

}
