package dao

import controllers.requests.bodies.CreateUser
import models.User
import org.joda.time.DateTime
import services.HashingService
import utils.{FutureO, GeneralUtils}

import scala.concurrent.{ExecutionContext, Future}

trait UserDAO
{
  def hashingService: HashingService

  def insert(user: User): Future[Int]

  def fetchByEmail(email: String): FutureO[User]

  def user(createUser: CreateUser)(implicit executionContext: ExecutionContext): Future[User] = for {
    saltedPasswordHash <- hashingService.hash(createUser.password)
    CreateUser(email, _, firstName, lastName) = createUser
    userId = GeneralUtils.randomUUID()
  } yield User(userId, DateTime.now(), email, saltedPasswordHash, firstName, lastName)
}