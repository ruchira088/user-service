package controllers.requests.bodies

import play.api.libs.json.{Json, OFormat}

case class LoginUser(email: String, password: String)

object LoginUser
{
  implicit val oFormat: OFormat[LoginUser] = Json.format[LoginUser]
}
