package com.example.model

import java.sql.Timestamp
import java.util.Date

import org.squeryl
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.annotations.Column
import org.squeryl.dsl.{ManyToOne, OneToMany}
import org.squeryl.{ForeignKeyDeclaration, KeyedEntity}

trait IdTrait extends KeyedEntity[Long]

class Author(val id: Long, val firstName: String, val lastName: String, val email: Option[String]) extends IdTrait {
  def this() = this(0, "", "", Some(""))

  override def toString: String = "{id:" + id + ",firstName:" + firstName + ",lastName:" + lastName + ",email:" + email.getOrElse(None) + "}";

  lazy val books: OneToMany[Book] = Library.authorBooks.left(this)
}

class Book(val id: Long, var title: String, @Column("coAuthorId") val authorId: Long, val coAuthorId: Option[Long]) extends IdTrait {
  def this() = this(0, "", 0, Some(0))

  lazy val author: ManyToOne[Author] = Library.authorBooks.right(this)
}

class Borrowal(val id: Long, val bookId: Long, val borrowerAccountId: Long, val scheduledToReturn: Date, val returnOn: Option[Timestamp],
               val numberOfPhonecallsForNoReturn: Int) extends IdTrait

object Library extends squeryl.Schema {

  val author = table[Author]("authors")

  val book = table[Book]

  val borrows = table[Borrowal]

  on(borrows)(b => declare(

    b.numberOfPhonecallsForNoReturn defaultsTo (0),
    b.borrowerAccountId is (indexed),
    columns(b.scheduledToReturn, b.borrowerAccountId) are indexed
  ))

  on(author)(b => declare(
    b.firstName is indexed,
    b.lastName is(indexed, dbType("varchar(255)")),
    b.email is(unique, indexed("mailIndex")),
    columns(b.firstName, b.lastName) are indexed
  ))

  on(book)(b => declare(
    b.title is indexed
  ))

  val authorBooks = oneToManyRelation(author, book) via ((a, b) => a.id === b.authorId)


  override def applyDefaultForeignKeyPolicy(foreignKeyDeclaration: ForeignKeyDeclaration) = foreignKeyDeclaration.unConstrainReference()

  authorBooks.foreignKeyDeclaration.constrainReference(onDelete cascade)

  //  override def columnNameFromPropertyName(propertyName: String) = {
  //    val camelCase: Array[String] = StringUtils.splitByCharacterTypeCamelCase(propertyName)
  //    camelCase.reduceRight((x, y) => x + "_" + y.toLowerCase)
  //  }
  //  NamingConventionTransforms

  override def columnNameFromPropertyName(propertyName: String) = NamingConventionTransforms.snakify(propertyName)
}

