package services

import scala.concurrent.Future

trait HashingService
{
  def hash(password: String): Future[String]

  def checkPassword(saltedPasswordHash: String, candidatePassword: String): Future[Boolean]
}
