package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class SegmentCategoryModel(
	id: Pk[Long],
	name: String
)


/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

object SegmentCategoryModel{

	val simple =  {
	    get[Pk[Long]]("SegmentCategories.id") ~
	    get[String]("SegmentCategories.name")  map {
	      case id~name => SegmentCategoryModel(id, name)
	    }
	}

	def list(page: Int = 0, pageSize: Int = 10, orderBy: String = "name", orderCrit: String = "asc", filter: String = "%"): Page[SegmentCategoryModel] = {
		val offset = pageSize * page
	    
	    DB.withConnection { implicit c =>
	      
	      val segmentCategories = SQL(
	        """
	          select id, name from SegmentCategories 
	          where SegmentCategories.name like {filter}
	          order by name desc
	          limit {pageSize} offset {offset}
	        """
	      ).on(
	        'pageSize -> pageSize, 
	        'offset -> offset,
	        'filter -> filter
	      ).as(SegmentCategoryModel.simple *)

	      val totalRows = SQL(
	        """
	          select count(*) from SegmentCategories 
	          where SegmentCategories.name like {filter}
	        """
	      ).on(
	        'filter -> filter
	      ).as(scalar[Long].single)

	      Page(segmentCategories, page, offset, totalRows)
	      
	    }
	}

	def findById(id: Long) : Option[SegmentCategoryModel] = {
    DB.withConnection { implicit connection =>
      SQL("select * from SegmentCategories where id = {id}").on('id -> id).as(SegmentCategoryModel.simple.singleOpt)
    }
  }
}