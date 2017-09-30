package services

import javax.inject.{Inject, Singleton}

import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future, blocking}

@Singleton
class BlowfishHashing @Inject()(implicit executionContext: ExecutionContext) extends HashingService
{
  def hash(password: String): Future[String] =
    Future {
      blocking {
        BCrypt.hashpw(password, BCrypt.gensalt())
      }
    }

  def checkPassword(saltedPasswordHash: String, candidatePassword: String): Future[Boolean] =
    Future {
      blocking {
        BCrypt.checkpw(candidatePassword, saltedPasswordHash)
      }
    }
}
