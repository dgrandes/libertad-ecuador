package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import anorm._

import views._
import models._

object Application extends Controller {

   /**
   * This result directly redirect to the application home.
   */
  val Home = Redirect(routes.Application.index)

  
  def index = Action {
    implicit request => Ok(views.html.index("",SegmentCategoryModel.list()))    
  }
  
  val segmentCategoryForm = Form(
  	mapping(
  		"id" -> ignored(NotAssigned:Pk[Long]),
  		"name" -> nonEmptyText
  		)(SegmentCategoryModel.apply)(SegmentCategoryModel.unapply)
  	)

  def addSegmentCategory = Action {
  	Ok(html.formSegmentCategory(segmentCategoryForm))
  }

  def saveSegmentCategory = Action { implicit request =>
  	segmentCategoryForm.bindFromRequest.fold(
  			formWithErrors => BadRequest(html.formSegmentCategory(formWithErrors)),
  			segmentCategory => {

          val resp = SegmentCategoryModel.add(segmentCategory)
          resp.errorMessage match{
            case Some(x) => Home.flashing("success" -> x)
            case None =>   Home.flashing("success" -> "Segment Category %s has been added".format(segmentCategory.name))

            }
          })

  }
}
