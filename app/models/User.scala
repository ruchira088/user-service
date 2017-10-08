package models

import utils.JsonFormatUtils._
import org.joda.time.DateTime
import play.api.libs.json.{JsObject, Json, OFormat}

case class User(
     id: String,
     createdAt: DateTime,
     email: String,
     saltedPasswordHash: Option[String],
     firstName: String,
     lastName: Option[String]
) {
  user =>

  def sanitize: User = user.copy(saltedPasswordHash = None)

  def toJson: JsObject = Json.toJsObject(user)
}

object User
{
  implicit val oFormat: OFormat[User] = Json.format[User]
}