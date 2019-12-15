package com.github.ivankliuk.crudser.domain

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

final case class User(email: Email, id: UserId, firstName: FirstName, lastName: LastName)

object User {

  implicit val encoder: Encoder[User] = deriveEncoder[User]

}
