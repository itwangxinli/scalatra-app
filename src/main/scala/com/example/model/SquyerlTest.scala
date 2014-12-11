package com.example.model

import com.example.data.init.DatabaseInit
import org.squeryl.{Query, SessionFactory}
import org.squeryl.PrimitiveTypeMode._
import Library._

object SquyerlTest extends DatabaseInit {
  def main(args: Array[String]) {
    configureDb();
    val session = SessionFactory.newSession;
    session.bindToCurrentThread
    initDB();
    val authors: Query[Author] = query

    for (author <- authors) {
      println(author.toString)
    }
    session.unbindFromCurrentThread
    closeDbConnection();
  }

  def initDB(): Unit = {
    Library.drop
    Library.create
    Library.printDdl
    val batche = List(new Author(23, "汪", "心利", Some("wangxinli@gmail.com")), new Author(23, "汪", "心利", Some("zhangsans@gmail.com")));

    inTransaction {
      author.insert(batche);
    }
  }


  def query = {

    from(author)(b => where(b.email === Some("wangxinli@gmail.com") or (b.email like Some("%gmail%")))
      select (b)
      orderBy (b.id asc)
    )
  }
}
