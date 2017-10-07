package modules

import com.google.inject.AbstractModule
import services.RedisCaching
import utils.ConfigUtils.getEnvValue

class RedisModule extends AbstractModule
{
  def configure() =
  {
    val (hostName, port) = {
      for {
        hostName <- getEnvValue("")
        port <- getEnvValue("")
      } yield (hostName, port)
    } getOrElse(("", ""))

    bind(classOf[RedisCaching]).asEagerSingleton()
  }
}
