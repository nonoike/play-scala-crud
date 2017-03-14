package services

import javax.inject.{Inject, Singleton}

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import scala.language.postfixOps

@Singleton
class TodoService @Inject()(dBApi: DBApi) {
  private val db = dBApi.database("default")

  val simple = {
    get[Option[Long]]("todo.id") ~
      get[String]("todo.name") map {
      case id ~ name => Todo(id, name)
    }
  }

  def list(): Seq[Todo] = {
    db.withConnection { implicit connection =>
      SQL(
        """
          |select * from todo
        """.stripMargin
      ).as(simple *)
    }
  }

  def insert(todo: Todo) = {
    db.withConnection { implicit collection =>
      SQL(
        """
          |insert into todo values ((select next value for todo_seq), {name})
        """.stripMargin
      ).on(
        'name -> todo.name
      ).executeUpdate()
    }
  }
}
