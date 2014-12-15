package com.example.model

import com.example.data.init.DatabaseInit


import org.squeryl.{Query, SessionFactory}
import org.squeryl.PrimitiveTypeMode._
import Library._

object SquyerlTest extends DatabaseInit {
  def main(args: Array[String]) {
    configureDb();
    val session = SessionFactory.newSession;
    session.setLogger(logger.info(_))
    session.bindToCurrentThread
    initDB();
    val authors: Query[Author] = query
    authors.foreach(a => {
      println(a);
      a.books.foreach(book => println(book.title))
    })
    author.delete(authors)
    session.unbindFromCurrentThread
    closeDbConnection();
  }


  def initDB(): Unit = {
    Library.drop
    Library.create
    Library.printDdl(println(_))
    val batche = List(new Author(23, "韩", "寒", Some("wangxinli@gmail.com")), new Author(23, "汪", "心利", Some("zhangsans@gmail.com")));
    val books = List(new Book(0, "红岭新闻乱播", 1, None), new Book(0, "红岭新闻乱播2", 1, None), new Book(0, "数据结构预算法分析", 2, None))
    inTransaction {
      val nAuthor: Author = new Author(23, "韩", "寒", Some("wangxinli@gmail.com"))
      val tbook: Book = new Book(0, "红岭新闻乱播", 1, None)
      author.insert(nAuthor);
      nAuthor.books.associate(tbook)

    }

  }

  def query = from(author)(a => where(a.id === 1) select (a))


}
