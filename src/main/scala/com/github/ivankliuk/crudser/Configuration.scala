package com.github.ivankliuk.crudser

import com.typesafe.config.ConfigFactory

object Configuration {
  private val config = ConfigFactory.load

  val serverHost = config.getString("server.host")
  val serverPort = config.getInt("server.port")

  val driver = config.getString("db.driver")
  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")

}
