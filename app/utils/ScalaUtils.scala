package utils

import exceptions.EmptyOptionException
import play.api.libs.json.{JsError, JsResult, JsSuccess}

import scala.concurrent.Future
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

  def toOption[A](value: A): Option[A] = value match {
    case null => None
    case _ => Some(value)
  }

  def predicate(boolean: Boolean, exception: => Throwable): Future[Unit] =
    if (boolean) Future.successful(()) else Future.failed(exception)
}
