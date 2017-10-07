package dao

import controllers.requests.bodies.CreateUser
import exceptions.IncorrectCredentialsException
import models.User
import org.joda.time.DateTime
import services.HashingService
import utils.{FutureO, GeneralUtils, ScalaUtils}

import scala.concurrent.{ExecutionContext, Future}

trait UserDAO
{
  def hashingService: HashingService

  def insert(user: User): Future[Int]

  def fetchByEmail(email: String): FutureO[User]

//  def verifyUser(email: String, password: String)(implicit executionContext: ExecutionContext): FutureO[User] = for {
//    user <- fetchByEmail(email)
//    success <- hashingService.checkPassword(user.saltedPasswordHash, password)
//    _ <- ScalaUtils.predicate(success, IncorrectCredentialsException)
//  } yield user

  def insert(createUser: CreateUser)(implicit executionContext: ExecutionContext): Future[User] = for {
    newUser <- user(createUser)
    result <- insert(newUser) if result == 1
  } yield newUser

  def user(createUser: CreateUser)(implicit executionContext: ExecutionContext): Future[User] = for {
    saltedPasswordHash <- hashingService.hash(createUser.password)
    CreateUser(email, _, firstName, lastName) = createUser
    userId = GeneralUtils.randomUUID()
  } yield User(userId, DateTime.now(), email, saltedPasswordHash, firstName, lastName)
}