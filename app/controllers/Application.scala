package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
  	try{
  		
  		Ok(views.html.index(""))
  	}catch{
  		case e: NoSuchElementException => Ok(views.html.index("No Such Element Exception"))
  	}
    
  }
  
}
