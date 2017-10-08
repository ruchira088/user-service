package utils

import ScalaUtils._
import org.joda.time.DateTime
import play.api.libs.json._

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

object JsonFormatUtils
{
  def parseIsoDate(isoDate: String): Try[DateTime] = try {
    Success(DateTime.parse(isoDate))
  } catch {
    case NonFatal(throwable) => Failure(throwable)
  }

  implicit val dateTimeFormat: Format[DateTime] = new Format[DateTime]
  {
    override def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString)

    override def reads(json: JsValue): JsResult[DateTime] = for {
      dateTimeString <- fromOption(json.asOpt[String])
      dateTime <- parseIsoDate(dateTimeString)
    } yield dateTime
  }
}
