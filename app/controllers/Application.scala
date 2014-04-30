package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

object Application extends Controller {

  /**
   * Describes the hello form.
   */
  val helloForm = Form(
    tuple(
      "pageTitle" -> nonEmptyText,
      "name" -> nonEmptyText,
      "repeat" -> number(min = 1, max = 100),
      "color" -> optional(text)
    )
  )

  // -- Actions

  /**
   * Home page
   */
  def index = Action {
    val randomNumber = new scala.util.Random.nextInt
    val pageTitle = if (randomNumber % 2 == 0) {
      "Even Number"
    } else {
      "Odd Number"
    } 
    Ok(html.index(pageTitle, helloForm))
  }

  /**
   * Handles the form submission.
   */
  def sayHello = Action { implicit request =>
    helloForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.index(formWithErrors)),
      {case (pageTitle, name, repeat, color) => Ok(html.hello(pageTitle, name, repeat.toInt, color))}
    )
  }

  def helloAgain = Action {
    Ok(html.helloAgain("...any title you want..."))
  }

}
