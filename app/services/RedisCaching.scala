package services

import javax.inject.{Inject, Singleton}

@Singleton
class RedisCaching @Inject() extends CachingService
{
  def get[A](key: String) = ???

  def set[A](key: String, value: A) = ???

  def delete(keys: String*) = ???
}
