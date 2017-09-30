package dao

import java.util.Date

import controllers.requests.bodies.CreateUser
import models.User
import services.HashingService
import types.FutureO
import utils.GeneralUtils

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
  } yield User(userId, new Date(), email, saltedPasswordHash, firstName, lastName)
}