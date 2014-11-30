package com.example.app

import com.example.data.init.DatabaseSessionSupport
import com.example.model.{Article, BlogDb}
import org.scalatra._
import org.scalatra.scalate.ScalateSupport
import org.squeryl.PrimitiveTypeMode._

class ArticlesController extends ScalatraWebAppStack
with SessionSupport
with DatabaseSessionSupport
with MethodOverride
with FlashMapSupport {

  get("/") {
    contentType = "text/html"

    val articles = from(BlogDb.articles)(select(_))

    ssp("articles/index", "articles" -> articles)

  }

  val newArticle = get("/articles/new") {
    contentType = "text/html"

    val article = new Article()
    ssp("/articles/new", "article" -> article)
  }

  post("/articles") {
    contentType = "text/html"

    val article = new Article(0, params("title"), params("body"))

    if (Article.create(article)) {
      flash("notice") = "Article successfully created"
      redirect("/")
    } else {
      flash("error") = "There were problems creating your article"
      ssp("articles/new", "article" -> article)
    }
  }

  get("/create-db") {
    contentType = "text/html"

    BlogDb.create
    redirect("/articles/new")
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

}