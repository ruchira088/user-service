package utils

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util.UUID

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

object GeneralUtils
{
  def randomUUID(): String = UUID.randomUUID().toString

  def convert[A, B](value: A)(conversion: A => B): Try[B] =
    try {
      Success(conversion(value))
    } catch {
      case NonFatal(throwable) => Failure(throwable)
    }
}
