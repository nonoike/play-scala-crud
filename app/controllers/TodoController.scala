package controllers

import javax.inject.Inject

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import services._
import views._

class TodoController @Inject()(todoService: TodoService,
                               val messagesApi: MessagesApi)
  extends Controller
    with I18nSupport {

  def helloWorld() = Action { implicit request =>
    Ok("Hello World")
  }

  def list() = Action { implicit request =>
    val items: Seq[Todo] = todoService.list()
    Ok(html.list(items))
  }

  def create() = Action {
    Ok(html.add(Forms.todoForm))
  }

  def save() = Action { implicit request =>
    val todoForm = Forms.todoForm.bindFromRequest().get
    todoService.insert(Todo(null, todoForm.todo.name))
    Redirect(routes.TodoController.list)
  }
}

case class TodoForm(command: Option[String], todo: Todo)

object Forms {
  def todoForm = Form(
    mapping(
      "command" -> optional(text),
      "db" -> mapping(
        "id" -> optional(longNumber),
        "name" -> nonEmptyText
      )
      ((id, name) => new Todo(id, name))
      ((n: Todo) => Some(n.id, n.name))
    )(TodoForm.apply)(TodoForm.unapply)
  )
}
