package services.authentication

import models.User
import utils.JsonFormatUtils._
import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}

case class AuthToken(bearerToken: String, expireAt: DateTime, user: User)

object AuthToken
{
  implicit val oFormat: OFormat[AuthToken] = Json.format[AuthToken]
}