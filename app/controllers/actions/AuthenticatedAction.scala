package controllers.actions

import javax.inject.Inject

import com.google.common.net.HttpHeaders
import controllers.requests.AuthenticatedRequest
import exceptions.{AuthorizationHeaderNotFoundException, InvalidAuthHeaderFormatException}
import org.apache.commons.lang3.StringUtils
import play.api.mvc.{ActionBuilderImpl, BodyParsers, Request, Result}
import services.authentication.AuthenticationService
import utils.ControllerUtils._
import utils.FutureO
import utils.ScalaUtils._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class AuthenticatedAction @Inject()(
         parser: BodyParsers.Default,
         authenticationService: AuthenticationService
 )(implicit executionContext: ExecutionContext)
  extends ActionBuilderImpl(parser)
{
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    for {
      bearerToken <- FutureO.fromTry(AuthenticatedAction.getBearerToken(request))
      authToken <- authenticationService.getAuthToken(bearerToken)
      result <- block(AuthenticatedRequest(authToken.user, request))
    } yield result
  }
      .flatten
      .recover(responseErrorHandler)
}

object AuthenticatedAction
{
  val AUTHENTICATION_SCHEME = "Bearer"

  def getBearerToken(request: Request[_]): Try[String] = for {
    authorizationHeader <- fromOption(
      request.headers.get(HttpHeaders.AUTHORIZATION),
      AuthorizationHeaderNotFoundException
    )
    bearerToken <- authorizationHeader.split(StringUtils.SPACE).toList.map(_.trim) match {
      case List(`AUTHENTICATION_SCHEME`, token) => Success(token)
      case _ => Failure(InvalidAuthHeaderFormatException)
    }
  } yield bearerToken
}
