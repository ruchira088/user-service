package exceptions

case class DuplicateUserException(email: String) extends Exception
