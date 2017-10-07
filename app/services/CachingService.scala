package services

import redis.{ByteStringDeserializer, ByteStringSerializer}
import utils.FutureO

import scala.concurrent.Future

trait CachingService
{
  def get[A](key: String)(implicit byteStringDeserializer: ByteStringDeserializer[A]): FutureO[A]

  def set[A](key: String, value: A)(implicit byteStringSerializer: ByteStringSerializer[A]): Future[Boolean]

  def delete(keys: String*): Future[Long]
}
