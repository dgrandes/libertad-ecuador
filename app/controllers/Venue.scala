package controllers
import models._
import views._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

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
      "name" -> nonEmptyText,
      "phone" -> nonEmptyText,
      "email" -> email,
      "tags" ->optional(text),
      "description" ->optional(text),
      "segmentId" -> longNumber,
      "place" -> mapping(
          "city" -> nonEmptyText,
          "state" -> nonEmptyText,
          "country" -> nonEmptyText
          )
      // The mapping signature matches the UserProfile case class signature,
      // so we can use default apply/unapply functions here
      (LocationModel.apply)(LocationModel.unapply),
      
      "accept" -> checked("You must accept the conditions")
      
    )
    // The mapping signature doesn't match the User case class signature,
    // so we have to define custom binding/unbinding functions
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (name, phone, email, tags, description, segmentId, place, _) => VenueModel(name, phone, email, tags, description, segmentId, place) 
    } 
    {
      // Unbinding: Create the mapping values from an existing User value
      (venue: VenueModel) => Some((venue.name, venue.phone, venue.email,venue.tags,  venue.description, venue.segmentId, venue.place, false))
    }.verifying(
      // Add an additional constraint: The username must not be taken (you could do an SQL request here)
      "This name is not available",
      venue => !Seq("venues").contains(venue.name)
    )
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
