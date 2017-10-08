package services.authentication

import javax.inject.{Inject, Singleton}

import dao.UserDAO
import exceptions.IncorrectCredentialsException
import org.joda.time.DateTime
import services.{CachingService, HashingService}
import utils.GeneralUtils.randomUUID
import utils.ScalaUtils._
import utils.{FutureO, ScalaUtils}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthenticationService @Inject()(userDAO: UserDAO, cachingService: CachingService, hashingService: HashingService)
                                     (implicit executionContext: ExecutionContext)
{
  def authenticate(email: String, password: String): Future[AuthToken] = for {
    user <- userDAO.fetchByEmail(email).flatten(IncorrectCredentialsException)
    saltedPasswordHash <- Future.fromTry(fromOption(user.saltedPasswordHash))
    success <- hashingService.checkPassword(saltedPasswordHash, password)
    _ <- ScalaUtils.predicate(success, IncorrectCredentialsException)
    authToken = AuthToken(randomUUID(), DateTime.now(), user.sanitize)
    _ <- cachingService.set(authToken.bearerToken, authToken)
  } yield authToken

  def getAuthToken(bearerToken: String): FutureO[AuthToken] =
    cachingService.get[AuthToken](bearerToken)
}
