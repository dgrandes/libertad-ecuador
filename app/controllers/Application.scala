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

  def addSegmentCategory = Action { implicit request =>
  	Ok(html.formSegmentCategory(segmentCategoryForm))
  }

  def saveSegmentCategory = Action { implicit request =>
  	segmentCategoryForm.bindFromRequest.fold(
  			formWithErrors => BadRequest(html.formSegmentCategory(formWithErrors)),
  			segmentCategory => {

          val resp = SegmentCategoryModel.add(segmentCategory)
          resp.errorMessage match{
            case Some(x) => Redirect(routes.Application.addSegmentCategory).flashing("success" -> x)
            case None =>   Home.flashing("success" -> "Segment Category '%s' has been added".format(segmentCategory.name))
            }
          })

  }

  def viewSegmentCategory(id: Long) = Action { implicit request =>
    Ok(html.displaySegmentCategory(SegmentCategoryModel.findById(id)))

  }

  def viewSegment(id: Long) = TODO

  def deleteSegmentCategory(id: Long) = Action { implicit request =>
    SegmentCategoryModel.delete(id) match {
      case None => BadRequest("invalid id")
      case Some(x) => Ok(x)
    }
  }

  def deleteSegmentCategoryConfirmed(name: String) = Action { implicit request =>
    Home.flashing("success" -> "Segment Category '%s' has been deleted!".format(name))
  }

  val segmentForm = Form(
    mapping(
    "id" -> ignored(NotAssigned:Pk[Long]),
    "name" -> nonEmptyText,
    "segmentCategoryId" -> longNumber
    )(SegmentModel.apply)(SegmentModel.unapply)
  )

  def addSegment = Action { implicit request =>
    Ok(html.formSegment(segmentForm))
  }

  def saveSegment = Action { implicit request =>
    segmentForm.bindFromRequest.fold(
        formWithErrors => BadRequest(html.formSegment(formWithErrors)),
        segment => {
          val resp = SegmentModel.add(segment)
          resp.errorMessage match {
              case Some(x) => Redirect(routes.Application.addSegment).flashing("error" -> x)
              case None => Home.flashing("success" -> "Segment '%s' has been added!".format(segment.name))
          }
        }
      )
  }
}
