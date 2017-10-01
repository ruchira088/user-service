package modules

import com.google.inject.AbstractModule
import services.RedisCaching

class RedisModule extends AbstractModule
{
  def configure() =
  {
    bind(classOf[RedisCaching]).asEagerSingleton()

  }
}
