package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class VenueModelSpec extends Specification{

	import models._

	def getVenueName(id: Long) = {
		VenueModel.findById(id) match {
	        	case Some(x) => x.name 
	        	case None =>  throw new Exception("Name not found")
	        }
	}

	def getVenueSegments(id: Long): Seq[SegmentModel] = VenueModel.segmentsOfVenue(id) match{
		case Some(x) => x.segments
		case None => throw new Exception("No Segments found for Venue "+id+"!")
	}

	"Venue Models Spec" should{
		"be able to retrieve by id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
	        
	      	getVenueName(1) must equalTo("My first Venue")
	      	getVenueName(2) must equalTo("My second Venue")
	      }
		}
		"be able to have several segments" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
	      	val segments = getVenueSegments(1)
	      	segments.size must greaterThan(1)
	      }
			
		} 
		
	}
}