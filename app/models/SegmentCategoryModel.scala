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

    def add(segmentCategory: SegmentCategoryModel): ModelResponse = {
    	try{
    		DB.withConnection{ implicit c =>
    			SQL("insert into SegmentCategories(name) values ({name})").on('name -> segmentCategory.name).executeUpdate()
    		}
    		ModelResponse(None, None)
    	}catch{
    		case e:Exception => {
    			if(e.getMessage.contains("CONSTRAINT_INDEX_B ON PUBLIC.SEGMENTCATEGORIES(NAME)"))
    				ModelResponse(None, Some("Segment Category Names must be unique!"))
    			else
    				ModelResponse(None, Some(e.getMessage))
    		}
    	}
    }
  
}