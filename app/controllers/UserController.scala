package controllers

import javax.inject.{Inject, Singleton}

import controllers.requests.bodies.{CreateUser, LoginUser}
import dao.UserDAO
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.authentication.AuthenticationService
import utils.ControllerUtils._
import utils.FutureO

import scala.concurrent.{ExecutionContext, Future}

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
        createUser <- Future.fromTry(deserialize[CreateUser])
        user <- userDAO.insert(createUser)
      } yield Ok(user.sanitize.toJson)
    }
  }

  def login() = Action.async(bodyParser.json) {
    implicit request: Request[JsValue] => {
      for {
        LoginUser(email, password) <- FutureO.fromTry(deserialize[LoginUser])
        authToken <- authenticationService.authenticate(email, password)
      } yield Ok(Json.toJson(authToken))
    }
      .flatten
      .recover(responseErrorHandler)
  }

}
