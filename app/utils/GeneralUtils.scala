package utils

import java.util.UUID

object GeneralUtils
{
  def randomUUID(): String = UUID.randomUUID().toString
}
