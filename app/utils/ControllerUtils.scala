package utils

import constants.ResponseMessages._
import exceptions.{IncorrectCredentialsException, RequestBodyValidationException}
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc.{Request, Result}

import scala.util.{Failure, Success, Try}

object ControllerUtils
{
  def deserialize[A](implicit request: Request[JsValue], reads: Reads[A]): Try[A] =
    request.body.validate[A]
      .fold(
        validationErrors => Failure(RequestBodyValidationException(validationErrors)),
        Success(_)
      )

  private def errorResponse[A](error: A)(implicit writes: Writes[A]): JsObject =
    Json.obj("error" -> Json.toJson[A](error))

  def responseErrorHandler: PartialFunction[Throwable, Result] = {
    case IncorrectCredentialsException => Unauthorized(errorResponse(INCORRECT_CREDENTIALS))
  }
}
