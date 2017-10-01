package services

import utils.FutureO

import scala.concurrent.Future

trait CachingService
{
  def get[A](key: String): FutureO[A]

  def set[A](key: String, value: A): Future[Boolean]

  def delete(keys: String*): Future[Long]
}
