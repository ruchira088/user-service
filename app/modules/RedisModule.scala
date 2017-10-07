package modules

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import constants.EnvironmentVariables._
import redis.RedisClient
import utils.ConfigUtils.getEnvValue
import utils.GeneralUtils._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class RedisModule extends AbstractModule
{
  val DEFAULT_REDIS_HOST = "redis"

  val DEFAULT_REDIS_PORT = 6379

  def configure() =
  {
    val (host, port) = {
      for {
        host <- getEnvValue(REDIS_HOST)
        portString <- getEnvValue(REDIS_PORT)
        port <- convert(portString)(_.toInt).toOption
      } yield (host, port)
    } getOrElse((DEFAULT_REDIS_HOST, DEFAULT_REDIS_PORT))

    implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()

    val redisClient = RedisClient(host = host, port = port)

    Await.ready(verifyRedisServer(redisClient), 30 seconds)

    println("Connection to the Redis server has been verified.")

    bind(classOf[RedisClient]).toInstance(redisClient)
  }

  def verifyRedisServer(redisClient: RedisClient): Future[String] = for {
    result <- {
      val ping = redisClient.ping()
      println("Redis ping sent !")
      ping
    }
    _ = println(s"Redis server replied with $result.")
  } yield result
}
