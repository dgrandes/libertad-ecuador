package models

import java.util.{Date}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class VenueModel(
  id: Pk[Long],
  name: String, 
  description: VenueDescription
)

case class VenueSegments(
  id: Pk[Long],
  segments: Seq[SegmentModel]
)

case class VenueDescription(
  email: String,
  phone: String,
  website: Option[String],
  description: String
)

case class LocationModel(
	city: String,
	state: String,
	country: String
)

object VenueModel{

  val selectVenueQuerybyId = SQL("""
    select * 
    from Venues as v, VenueDescriptions as vd
    where v.id = vd.idVenue and v.id = {id}
    """
  )

  val venueResultParser = {
    get[Pk[Long]]("Venues.id") ~
    str("Venues.name") ~
    str("VenueDescriptions.email") ~
    str("VenueDescriptions.phone") ~
    get[Option[String]]("VenueDescriptions.website") ~
    str("VenueDescriptions.description") map { 
      case id ~ name ~ email ~ phone ~ website ~ description => VenueModel(id, name, VenueDescription(email, phone, website, description))
    }
  }

  val selectVenueSegments = SQL("""
      select VenueSegments.idVenue, Segments.id, Segments.name, Segments.segmentCategoryId
      from Segments join VenueSegments on Segments.id = VenueSegments.idSegment
      where VenueSegments.idVenue = {id}
    """)

  def venueSegmentsParser = {
    get[Pk[Long]]("VenueSegments.idVenue") ~
    get[Pk[Long]]("Segments.id") ~
    str("Segments.name") ~
    get[Long]("Segments.segmentCategoryId") map {
      case idVenue~id~name~segmentCategoryId => (idVenue, id,  name, segmentCategoryId) 
    } 
  }

	def all(): List[VenueModel] = Nil

	def create(label: String){}

	def delete(id: Long) {}

  def findById(id: Long) : Option[VenueModel] = {
      DAO.withConnection { implicit connection =>
         selectVenueQuerybyId.on('id -> id).as(VenueModel.venueResultParser.singleOpt)
      }
  }

  def fetchVenueSegmentTuples(id: Long):List[(Pk[Long], Pk[Long], String, Long)] = {
      DAO.withConnection{implicit connection =>
        selectVenueSegments.on('id -> id).as(VenueModel.venueSegmentsParser *)
      }
  }

  def segmentsOfVenue(id: Long) : Option[VenueSegments] = {
        val tuples = fetchVenueSegmentTuples(id)

        tuples.headOption.map{ t => 
            VenueSegments(t._1, tuples.map{
              r => SegmentModel(r._2,r._3,r._4)
              })
          }        
  }

  
}