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

  def serializeObject[A](obj: A): List[Byte] =
  {
    val byteArrayStream = new ByteArrayOutputStream()
    val objectOutputStream = new ObjectOutputStream(byteArrayStream)

    objectOutputStream.writeObject(obj)
    objectOutputStream.close()

    byteArrayStream.toByteArray.toList
  }
}
