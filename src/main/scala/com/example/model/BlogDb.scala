package com.example.model


import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import org.squeryl.Query
import org.squeryl.KeyedEntity
import org.squeryl.PersistenceStatus

/**
 * Created by xinliwang on 14/11/29.
 */
class Article(val id: Long, val title: String, val body: String) extends ScalatraRecord {

  def this() = this(0, "default title", "default body")

}

/**
 * The BlogDb object acts as a cross between a Dao and a Schema definition file.
 */
object BlogDb extends Schema {

  val articles = table[Article]("articles")

  on(articles)(a => declare(
    a.id is (autoIncremented),
    a.body is (indexed("abody"))
  ))


}

object Article extends {

  def create(article: Article): Boolean = {
    inTransaction {
      val result = BlogDb.articles.insert(article)

      result.isPersisted
    }
  }

}

/**
 * This trait is just a way to aggregate our model style across multiple
 * models so that we have a single point of change if we want to add
 * anything to our model behaviour
 */
trait ScalatraRecord extends KeyedEntity[Long] with PersistenceStatus {}


