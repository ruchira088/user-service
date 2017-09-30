package utils

import exceptions.RequestBodyValidationException
import play.api.libs.json.{JsValue, Reads}
import play.api.mvc.Request

import scala.util.{Failure, Success, Try}

object ControllerUtils
{
  def deserialize[A](implicit request: Request[JsValue], reads: Reads[A]): Try[A] =
    request.body.validate[A]
      .fold(
        validationErrors => Failure(RequestBodyValidationException(validationErrors)),
        Success(_)
      )
}
