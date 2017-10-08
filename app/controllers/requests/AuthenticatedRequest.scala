package controllers.requests

import models.User
import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A](user: User, request: Request[A]) extends WrappedRequest(request)
