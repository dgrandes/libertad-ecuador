package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._

import anorm.SqlParser._

import play.api._
import play.api.libs._

import play.core._

import java.sql._
import javax.sql._

import com.jolbox.bonecp._
import com.jolbox.bonecp.hooks._



case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}
case class ModelResponse(response: Option[AnyRef], errorMessage: Option[String])

object DAO{

	def withConnection[A](block: Connection => A)(implicit app: Application): A = {
	    DB.withConnection(config.Global.env)(block)
	  }
}