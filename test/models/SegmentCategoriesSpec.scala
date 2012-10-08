package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class SegmentCategoriesSpec extends Specification{

	import models._

	"Segment Categories Model" should {

		"be listed" in {
	      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
	        
	        val segmentCategories = SegmentCategoryModel.list()

	        segmentCategories.total must equalTo(2)
	        segmentCategories.items must have length(2)

	      }
	    }
		"be retrieved by id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
	        val Some(restaurantes) = SegmentCategoryModel.findById(1)
	      
	        restaurantes.name must equalTo("Restaurants")
	      }
		}
	}
}