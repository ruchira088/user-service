package models

import java.util.Date

import constants.GeneralConstants
import play.api.libs.json.{JsObject, Json, OFormat}

case class User(
  id: String,
  createdAt: Date,
  email: String,
  saltedPasswordHash: String,
  firstName: String,
  lastName: Option[String]
) {
  user =>

  def sanitize: User = user.copy(saltedPasswordHash = GeneralConstants.MASKED_FIELD)

  def toJson: JsObject = Json.toJsObject(user)
}

object User
{
  implicit val oFormat: OFormat[User] = Json.format[User]
}