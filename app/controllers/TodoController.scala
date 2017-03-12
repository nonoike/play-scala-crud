package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import views._

class TodoController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def helloWorld() = Action { implicit request =>
    Ok("Hello World")
  }

  def list() = Action { implicit request =>
    val message: String = "ここにリストを表示"
    Ok(html.list(message))
  }
}
