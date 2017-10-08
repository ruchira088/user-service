package services.authentication

import javax.inject.{Inject, Singleton}

import dao.UserDAO
import exceptions.IncorrectCredentialsException
import org.joda.time.DateTime
import services.{CachingService, HashingService}
import utils.FutureO._
import utils.GeneralUtils.randomUUID
import utils.{FutureO, ScalaUtils}

import scala.concurrent.ExecutionContext

@Singleton
class AuthenticationService @Inject()(userDAO: UserDAO, cachingService: CachingService, hashingService: HashingService)
                                     (implicit executionContext: ExecutionContext)
{
  def authenticate(email: String, password: String): FutureO[AuthToken] = for {
    user <- userDAO.fetchByEmail(email)
    success <- hashingService.checkPassword(user.saltedPasswordHash, password)
    _ <- ScalaUtils.predicate(success, IncorrectCredentialsException)
    authToken = AuthToken(randomUUID(), DateTime.now(), user.sanitize)
    _ <- cachingService.set(authToken.bearerToken, authToken)
  } yield authToken
}
