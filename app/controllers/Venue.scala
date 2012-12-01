package controllers
import models._
import views._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._

object Venue extends Controller {

    /**
   * Sign Up Form definition.
   *
   * Once defined it handle automatically, ,
   * validation, submission, errors, redisplaying, ...
   */
  val venueForm: Form[VenueModel] = Form(
    
    // Define a mapping that will handle User values
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> mapping (
        "phone" -> nonEmptyText,
        "email" -> nonEmptyText,
        "website" -> optional(text),
        "description" -> nonEmptyText
      )(VenueDescription.apply)(VenueDescription.unapply),
      "accept" -> checked("You must accept the conditions")
      
    )
    {
      (id, name, description, accept) => VenueModel(id, name, description) 
    } 
    {
      (venue: VenueModel) => Some((venue.id, venue.name, venue.description, false))
    }
  )

  /**
   * Display an empty form.
   */
  def index = Action {
    Ok(html.formVenue(venueForm));
  }
  
    /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    venueForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.formVenue(errors)),
      venue => Ok(html.summary(venue))
    )
  }
  
}
