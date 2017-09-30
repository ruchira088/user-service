package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import dao.{MongoUserDAO, UserDAO}
import services.{BlowfishHashing, HashingService}

class GuiceModule extends AbstractModule
{
  def configure() = {
    bind(classOf[UserDAO])
//      .annotatedWith(Names.named(""))
      .to(classOf[MongoUserDAO])

    bind(classOf[HashingService])
      .to(classOf[BlowfishHashing])
  }

}
