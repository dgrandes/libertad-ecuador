package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class Segmen extends Specification{

	import models._

	"Venue Model" should {
		"be retrieved by id" in {
			true
	/*		running(FakeApplication(additionalConfiguration = inMemoryDatabase("default-test"))) {
        
	        val Some(venue) = VenueModel.findById(21)
	      
	        macintosh.name must equalTo("Macintosh")
	        macintosh.introduced must beSome.which(dateIs(_, "1984-01-24"))  
	        
	      }*/
		}
	}
}