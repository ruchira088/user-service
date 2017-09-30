package controllers.requests.bodies

import play.api.libs.json.{Json, OFormat}

case class CreateUser(email: String, password: String, firstName: String, lastName: Option[String])

object CreateUser
{
  implicit val oFormat: OFormat[CreateUser] = Json.format[CreateUser]
}
