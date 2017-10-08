package exceptions

import play.api.libs.json.{JsPath, JsonValidationError}

case class RequestBodyValidationException(validationErrors: List[(JsPath, List[JsonValidationError])]) extends Exception

object RequestBodyValidationException
{
  def apply(validationErrors: Seq[(JsPath, Seq[JsonValidationError])]) =
    new RequestBodyValidationException(validationErrors.toList.map {
      case (jsPath, errorList) => (jsPath, errorList.toList)
    })
}