package com.github.ivankliuk.crudser

import io.circe.Json

object errors {

  trait JsonResponseBody[A] {
    def toJson(value: A): Json
  }

  object instances {

    implicit val jsonResponseBody: JsonResponseBody[String] =
      (value: String) => Json.obj {
        ("message", Json.fromString(value))
      }

  }

  object syntax {

    implicit class JsonResponseBodyOps[A](value: A) {
      def toJson(implicit body: JsonResponseBody[A]): Json = body.toJson(value)
    }

  }

}
