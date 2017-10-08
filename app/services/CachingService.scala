package services

import play.api.libs.json.{Reads, Writes}
import utils.FutureO

import scala.concurrent.Future

trait CachingService
{
  def get[A](key: String)(implicit reads: Reads[A]): FutureO[A]

  def set[A](key: String, value: A)(implicit writes: Writes[A]): Future[Boolean]

  def delete(keys: String*): Future[Long]
}
