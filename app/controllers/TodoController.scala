package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import services._
import views._

class TodoController @Inject()(todoService: TodoService, val messagesApi: MessagesApi)
  extends Controller with I18nSupport {

  def helloWorld() = Action { implicit request =>
    Ok("Hello World")
  }

  def list() = Action { implicit request =>
    val items: Seq[Todo] = todoService.list()
    Ok(html.list(items))
  }
}
