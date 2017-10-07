package services

import javax.inject.Inject

import redis.{ByteStringDeserializer, ByteStringSerializer, RedisClient}
import utils.FutureO

import scala.concurrent.ExecutionContext

class RedisCaching @Inject()(redisClient: RedisClient)(implicit executionContext: ExecutionContext)
  extends CachingService
{
  def get[A](key: String)(implicit byteStringDeserializer: ByteStringDeserializer[A]) =
    FutureO(redisClient.get[A](key))

  def set[A](key: String, value: A)(implicit byteStringSerializer: ByteStringSerializer[A]) =
    redisClient.set[A](key, value)

  def delete(keys: String*) = redisClient.del(keys: _*)

}
