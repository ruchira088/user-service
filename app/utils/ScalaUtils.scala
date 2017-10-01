package utils

import exceptions.EmptyOptionException
import play.api.libs.json.{JsError, JsResult, JsSuccess}

import scala.util.{Failure, Success, Try}

object ScalaUtils
{
  implicit def fromTry[A](tryValue: Try[A]): JsResult[A] = tryValue match {
    case Success(value) => JsSuccess(value)
    case Failure(throwable) => JsError(throwable.getMessage)
  }

  def fromOption[A](option: Option[A]): Try[A] = option match {
    case Some(value) => Success(value)
    case None => Failure(EmptyOptionException)
  }
}