package dao

import javax.inject.{Inject, Singleton}

import models.User
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import services.HashingService
import utils.FutureO

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MongoUserDAO @Inject()(
        reactiveMongoApi: ReactiveMongoApi,
        val hashingService: HashingService
    )(implicit executionContext: ExecutionContext) extends UserDAO
{
  def getCollection: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection[JSONCollection](MongoUserDAO.COLLECTION_NAME))

  def insert(user: User): Future[Int] = for {
    collection <- getCollection
    writeResult <- collection.insert(user)
  } yield writeResult.n

  def fetchByEmail(email: String): FutureO[User] = FutureO {
    for {
      collection <- getCollection
      userOption <- collection.find(Json.obj("email" -> email)).cursor[User]().headOption
    } yield userOption
  }

  def exists(email: String) = fetchByEmail(email).future.map {
    case None => false
    case _ => true
  }
}

object MongoUserDAO
{
  val COLLECTION_NAME = "user"
}
