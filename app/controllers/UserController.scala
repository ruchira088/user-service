package controllers

import javax.inject.{Inject, Singleton}

import controllers.requests.bodies.CreateUser
import dao.UserDAO
import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, ControllerComponents, PlayBodyParsers, Request}
import utils.ControllerUtils._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(
        controllerComponents: ControllerComponents,
        bodyParser: PlayBodyParsers,
        userDAO: UserDAO
    )(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)
{
  def create() = Action.async(bodyParser.json) {
    implicit request: Request[JsValue] => {
      for {
        createUser <- Future.fromTry(deserialize[CreateUser])
        user <- userDAO.user(createUser)
        writeResult <- userDAO.insert(user) if writeResult == 1
      } yield Ok(user.sanitize.toJson)
    }
  }

}
