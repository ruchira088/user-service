package controllers

import javax.inject.{Inject, Singleton}

import controllers.requests.bodies.{CreateUser, LoginUser}
import dao.UserDAO
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.authentication.{AuthToken, AuthenticationService}
import utils.ControllerUtils._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future._

@Singleton
class UserController @Inject()(
        controllerComponents: ControllerComponents,
        bodyParser: PlayBodyParsers,
        userDAO: UserDAO,
        authenticationService: AuthenticationService
    )(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)
{

  def create(): Action[JsValue] = Action.async(bodyParser.json) {
    implicit request: Request[JsValue] => {
      for {
        createUser <- fromTry(deserialize[CreateUser])
        user <- userDAO.insert(createUser)
      } yield Ok(user.sanitize.toJson)
    }
        .recover(responseErrorHandler)
  }

  def login() = Action.async(bodyParser.json) {
    implicit request: Request[JsValue] => {
      for {
        LoginUser(email, password) <- fromTry(deserialize[LoginUser])
        AuthToken(bearerToken, _, _) <- authenticationService.authenticate(email, password)
      } yield Ok(Json.obj("bearerToken" -> bearerToken))
    }
      .recover(responseErrorHandler)
  }

}
