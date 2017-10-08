package modules

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import constants.EnvironmentVariables._
import redis.RedisClient
import utils.ConfigUtils.getEnvValue
import utils.GeneralUtils._
import utils.SystemUtils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class RedisModule extends AbstractModule
{
  val DEFAULT_REDIS_HOST = "localhost"

  val DEFAULT_REDIS_PORT = 6379

  def configure() =
  {
    val redisHost = getEnvValue(REDIS_HOST) getOrElse DEFAULT_REDIS_HOST

    val redisPort = getEnvValue(REDIS_PORT)
      .flatMap(port => convert(port)(_.toInt).toOption)
      .getOrElse(DEFAULT_REDIS_PORT)

    implicit val akkaSystem: ActorSystem = akka.actor.ActorSystem()

    val redisClient = RedisClient(host = redisHost, port = redisPort)

    val verification = verifyRedisServer(redisClient)

    verification.onComplete {
      case Success(_) =>
        println("Connection to the Redis server has been verified.")
      case Failure(throwable) =>
        SystemUtils.terminate(throwable, "Failed to verify connection with Redis server")
    }

    Await.ready(verification, 30 seconds)

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
