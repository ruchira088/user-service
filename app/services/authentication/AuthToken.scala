package services.authentication

import akka.util.ByteString
import models.User
import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}
import redis.{ByteStringDeserializer, ByteStringSerializer}
import utils.GeneralUtils

case class AuthToken(bearerToken: String, expireAt: DateTime, user: User)

object AuthToken
{
//  implicit val oFormat: OFormat[AuthToken] = Json.format[AuthToken]

//  implicit val deserializer: ByteStringDeserializer[AuthToken] = byteString => byteString.toList

  implicit val serializer: ByteStringSerializer[AuthToken] =
    authToken => ByteString(GeneralUtils.serializeObject[AuthToken](authToken).toArray)
}