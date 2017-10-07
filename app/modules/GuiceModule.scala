package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import dao.{MongoUserDAO, UserDAO}
import redis.ByteStringSerializer
import services.authentication.AuthToken
import services.{BlowfishHashing, CachingService, HashingService, RedisCaching}

class GuiceModule extends AbstractModule
{
  def configure() = {
    bind(classOf[UserDAO])
//      .annotatedWith(Names.named(""))
      .to(classOf[MongoUserDAO])

    bind(classOf[HashingService])
      .to(classOf[BlowfishHashing])

    bind(classOf[CachingService])
      .to(classOf[RedisCaching])
  }

}
