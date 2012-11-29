package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class SegmentModel(
	id: Pk[Long],
	name: String,
	segmentCategoryId: Long
)

object SegmentModel{

	val simple =  {
	    get[Pk[Long]]("Segments.id") ~
	    get[String]("Segments.name") ~
	    get[Long]("Segments.segmentCategoryId")  map {
	      case id~name~segmentCategoryId => SegmentModel(id, name, segmentCategoryId)
	    }
	}

	def findById(id: Long) : Option[SegmentModel] = {
    	DAO.withConnection { implicit connection =>
      		SQL("select * from Segments where id = {id}").on('id -> id).as(SegmentModel.simple.singleOpt)
    	}
	}

    def add(segment: SegmentModel): ModelResponse = {
    	try{
    		DAO.withConnection{ implicit c =>
    			SQL("insert into Segments(name, segmentCategoryId) values ({name},{segmentCategoryId})").on(
    				'name -> segment.name,
    				'segmentCategoryId -> segment.segmentCategoryId).executeUpdate()
    		}
    		ModelResponse(None, None)
    	}catch{
    		case e:Exception => { 
    				ModelResponse(None, Some(e.getMessage))
    		}
    	}
    }

    def listSegmentsFrom(segmentCategoryId: Long, page: Int = 0,  pageSize: Int = 10, orderBy: String = "name", 
    	orderCrit: String = "asc", filter: String = "%"): Page[SegmentModel] = {
    	val offset = pageSize * page

    	DAO.withConnection{ implicit c =>
    		val segments = SQL (
    			"""
    				select id, name, segmentCategoryId from Segments
    				where Segments.name like {filter}
    				and Segments.segmentCategoryId = {segmentCategoryId}
    				order by name desc
    				limit {pageSize} offset {offset}
    			"""
    			).on(
    				'pageSize -> pageSize,
    				'offset -> offset,
    				'filter -> filter,
    				'segmentCategoryId -> segmentCategoryId
    			).as(SegmentModel.simple *)

    			val totalRows = SQL(
    				"""
    					select count(*) from Segments
    					where Segments.name like {filter}
    					and Segments.segmentCategoryId = {segmentCategoryId}
    				"""
    			).on('filter -> filter, 'segmentCategoryId -> segmentCategoryId).as(scalar[Long].single)

    			Page(segments, page, offset, totalRows)

    	}

    }

   /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DAO.withConnection { implicit connection =>
    SQL("select * from Segments order by name").as(SegmentModel.simple *).map(s => s.id.toString -> s.name)
  }

  def optionsForSegment(id: Long): Seq[(String, String)] = DAO.withConnection { implicit connection =>
    SQL("select * from Segments where Segments.segmentCategoryId = {id} order by name").on('id -> id).as(SegmentModel.simple *).map(s => s.id.toString -> s.name)
  }
  
}