package dao

import controllers.requests.bodies.CreateUser
import exceptions.DuplicateUserException
import models.User
import org.joda.time.DateTime
import services.HashingService
import utils.{FutureO, GeneralUtils, ScalaUtils}

import scala.concurrent.{ExecutionContext, Future}

trait UserDAO
{
  def hashingService: HashingService

  def insert(user: User): Future[Int]

  def exists(email: String): Future[Boolean]

  def fetchByEmail(email: String): FutureO[User]

  def insert(createUser: CreateUser)(implicit executionContext: ExecutionContext): Future[User] = for {
    userExists <- exists(createUser.email)
    _ <- ScalaUtils.predicate(!userExists, DuplicateUserException(createUser.email))
    newUser <- user(createUser)
    result <- insert(newUser) if result == 1
  } yield newUser

  def user(createUser: CreateUser)(implicit executionContext: ExecutionContext): Future[User] = for {
    saltedPasswordHash <- hashingService.hash(createUser.password)
    CreateUser(email, _, firstName, lastName) = createUser
    userId = GeneralUtils.randomUUID()
  } yield User(userId, DateTime.now(), email, Some(saltedPasswordHash), firstName, lastName)
}