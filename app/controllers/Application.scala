package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
  	try{
  		val vcap = sys.env("VCAP_SERVICES")
  		Ok(views.html.index(vcap))
  	}catch{
  		case e: NoSuchElementException => Ok(views.html.index("No Such Element Exception"))
  	}
    
  }
  
}
