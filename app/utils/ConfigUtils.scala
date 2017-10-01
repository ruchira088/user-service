package utils

object ConfigUtils
{
  def getEnvValue(name: String): Option[String] = ScalaUtils.toOption(System getenv name)
}
